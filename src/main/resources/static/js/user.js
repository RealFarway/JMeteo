$(function() {
    $("#cityName").autocomplete({
        source: function(request, response) {
            $.ajax({
                url: "https://api.opencagedata.com/geocode/v1/json",
                dataType: "json",
                data: {
                    key: "YOUR_OPENCAGE_API",
                    q: request.term,
                    limit: 10
                },
                success: function(data) {
                    response($.map(data.results, function(item) {
                        return {
                            label: item.formatted,
                            value: item.formatted,
                            lat: item.geometry.lat,
                            lon: item.geometry.lng
                        };
                    }));
                }
            });
        },
        minLength: 2,
        select: function(event, ui) {
            $("#cityLat").val(ui.item.lat);
            $("#cityLon").val(ui.item.lon);
            $("#addCityButton").prop("disabled", false);
        }
    });
});

$("#cityName").on("keyup", function() {
    $("#addCityButton").prop("disabled", true);
});