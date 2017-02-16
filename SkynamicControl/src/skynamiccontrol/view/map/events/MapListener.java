package skynamiccontrol.view.map.events;

/**
 * MapListener allows to listen the map events.
 */
public interface MapListener {
    void onMapClick(MapEvent event);
    void onMapDoubleClick(MapEvent event);
    void onMapRightClick(MapEvent event);
    void onMapDrag();
    void onMapDragStart();
    void onMapDragEnd();
    void onMapTypeChanged(String mapType);
    void onMapMouseMove(MapEvent event);
    void onMapMouseOut(MapEvent event);
    void onMapMouseOver(MapEvent event);
    void onMapTilesLoaded();
    void onMapZoomChanged(int zoomValue);

}
