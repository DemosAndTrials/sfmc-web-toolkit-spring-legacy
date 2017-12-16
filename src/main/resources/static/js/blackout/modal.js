requirejs.config({
    paths: {
        postmonger: 'js/postmonger'
    }
});

// Postmonger Events
// https://developer.salesforce.com/docs/atlas.en-us.noversion.mc-app-development.meta/mc-app-development/using-postmonger.htm
define(['postmonger'], function (Postmonger) {
    'use strict';

    console.log('*** ' + window.location.href + ' ***');

    var connection = new Postmonger.Session();
    var inArgPayload = {};


    $(window).ready(function () {
        console.log("modal ready");
        connection.trigger('ready');
    });

    // - Broadcast in response to the first ready event called by the custom application.
    //   This is typically done on $(window).ready()
    // - Response (payload): { name: 'MyActivity', metaData: {}, arguments: {}, configurationArguments: {}, outcomes: {} }
    // - When the activity is dragged from the activity list initially (meaning that it has no existing data),
    //   the default activity structure is pulled from the custom application's config.json.
    //   If the activity is a configured activity, the existing saved JSON structure of the activity is passed.
    connection.on('initActivity', function (payload) {
        console.log('initActivity');
        if (payload) {
            inArgPayload = payload;

            console.log('payload', JSON.stringify(payload));
        }
    });
});
