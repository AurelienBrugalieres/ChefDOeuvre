function initialize() {
    var latlng = new google.maps.LatLng(43.4625, 1.273611111111111);

    var myOptions = {
        zoom: 18,
        center: latlng,
        mapTypeId: google.maps.MapTypeId.SATELLITE,
        disableDoubleClickZoom: true,
        keyboardShortcuts: true,
        scrollwheel: true,
        draggable: true,
        disableDefaultUI: false, // Completly disable all controls.
        mapTypeControl: false, // Allow to change map type.
        overviewMapControl: false, // Small window of overview.
        panControl: false, // Disc used to pan the map.
        rotateControl: false, // Scale slider?
        navigationControl: false, // Scale slider?
        streetViewControl: false, // Place a streetview camera.
        scaleControl: false, // Scale slider?
        zoomControl: false, // Scale slider?
        backgroundColor: "#666970"
    };

    document.geocoder = new google.maps.Geocoder();
    document.map = new google.maps.Map(document.getElementById("map_canvas"), myOptions);

    document.zoomIn = function zoomIn() {
        var zoomLevel = document.map.getZoom();
        if (zoomLevel <= 20)
            document.map.setZoom(zoomLevel + 1);
    };

    document.zoomOut = function zoomOut() {
        var zoomLevel = document.map.getZoom();
        if (zoomLevel > 0)
            document.map.setZoom(zoomLevel - 1);
    };

    document.setMapTypeRoad = function setMapTypeRoad() {
        document.map.setMapTypeId(google.maps.MapTypeId.ROADMAP);
    };
    document.setMapTypeSatellite = function setMapTypeSatellite() {
        document.map.setMapTypeId(google.maps.MapTypeId.SATELLITE);
    };
    document.setMapTypeHybrid = function setMapTypeHybrid() {
        document.map.setMapTypeId(google.maps.MapTypeId.HYBRID);
    };
    document.setMapTypeTerrain = function setMapTypeTerrain() {
        document.map.setMapTypeId(google.maps.MapTypeId.TERRAIN);
    };

    document.goToLocation = function goToLocation(searchString) {
        document.geocoder.geocode({'address': searchString}, function (results, status) {
            if (status == google.maps.GeocoderStatus.OK) {
                document.map.setCenter(results[0].geometry.location);
            } else {
                alert("Geocode was not successful for the following reason: " + status);
            }
        });
    };

    document.map.addListener('click', onMapClick);

}

function onMapClick(event) {
    java.onMapClick(event.latLng);
}

function createMarker() {
    var marker = new google.maps.Marker({
        position: event.latLng,
        map: document.map,
        draggable: true,
        title: "New waypoint",
        icon: {
            url: "../../../../../resources/bitmaps/waypoint32x32.png"
        }
    });
}
