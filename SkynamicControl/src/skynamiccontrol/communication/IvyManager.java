package skynamiccontrol.communication;

import fr.dgac.ivy.Ivy;
import fr.dgac.ivy.IvyClient;
import fr.dgac.ivy.IvyException;

import java.util.ArrayList;
import java.util.Observable;

/**
 * Created by fabien on 13/02/17.
 */
public class IvyManager extends Observable{

    private Ivy bus;
    private ArrayList<String> regexes;

    public void initIvyBus(String name, String readyMsg, String address) {
        if(bus != null) {
            bus.stop();
        }
        bus = new Ivy(name, readyMsg, null);
        try {
            bus.start(address);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        bus.stop();
    }

    public int registerRegex(String regex) {
        //if the regex is already registered, just return its id.
        int regexNb;
        if((regexNb = getRegexNumber(regex)) != -1) {
            return regexNb;
        }
        //else, register it.
        int newRegexNumber = regexes.size();
        try {
            bus.bindMsg(regex, (IvyClient ivyClient, String[] strings) -> {
                setChanged();
                notifyObservers(new IncomeMessage(newRegexNumber, strings));
            });
            regexes.add(regex);
            return newRegexNumber;
        } catch (IvyException e) {
            e.printStackTrace();
            return -1;
        }
    }

    private int getRegexNumber(String regex) {
        for (int i = 0; i < regexes.size(); i++) {
            if(regexes.get(i).equals(regex)) {
                return i;
            }
        }
        return -1;
    }


    public boolean sendMessage(String message) {
        try {
            bus.sendMsg(message);
            return true;
        } catch (IvyException e) {
            e.printStackTrace();
            return false;
        }

    }


    private static IvyManager ourInstance = new IvyManager();

    public static IvyManager getInstance() {
        return ourInstance;
    }

    private IvyManager() {
        regexes = new ArrayList<>();
    }
}
