requirejs.config({
    paths: {
        postmonger: 'js/postmonger'
    }
});

// Postmonger Events
// https://developer.salesforce.com/docs/atlas.en-us.noversion.mc-app-development.meta/mc-app-development/using-postmonger.htm
define(['postmonger'], function(Postmonger) {
    'use strict';

    console.log('*** ' + window.location.href  + ' ***');

    var connection = new Postmonger.Session();

    var tokens;
    var endpoints;
    var inArgPayload = {};
    var step = 1;

    // get the # of steps
    // get it from hidden field
    var numSteps = $('#numSteps').val();
    //var numSteps = getUrlParameter('numSteps');
    // do some error checking on the inbound num steps
    console.log("numSteps: " + numSteps);


    $(window).ready(function() {
        console.log("ready + request Endpoints");
        connection.trigger('ready');

        // TODO just for testing
        connection.trigger('requestEndpoints');
        connection.trigger('requestTokens');
    });

    // - Broadcast in response to the first ready event called by the custom application.
    //   This is typically done on $(window).ready()
    // - Response (payload): { name: 'MyActivity', metaData: {}, arguments: {}, configurationArguments: {}, outcomes: {} }
    // - When the activity is dragged from the activity list initially (meaning that it has no existing data),
    //   the default activity structure is pulled from the custom application's config.json.
    //   If the activity is a configured activity, the existing saved JSON structure of the activity is passed.
    connection.on('initActivity', function(payload) {
        console.log('initActivity');
        if (payload) {
            inArgPayload = payload;

            console.log('payload',JSON.stringify(payload));
        }
    });

    // - Broadcast in response to a requestTokens event called by the custom application.
    //   Journey Builder passes back an object containing both a legacy token and a Fuel2 token.
    // - Response (tokens): { token: <legacy token>, fuel2token: <fuel api token> }
    connection.on('requestedTokens', function( data ) {
        if( data.error ) {
            console.error( data.error );
        } else {
            tokens = data;
        }
        console.log('*** requestedTokens ***', JSON.stringify(data));
    });

    // - Broadcast in response to a requestEndpoints event called by the custom application.
    //   Journey Builder passes back an object containing a REST host URL.
    // - Response (endpoints): { restHost: <url> } i.e. "rest.s1.qa1.exacttarget.com"
    connection.on('requestedEndpoints', function( data ) {
        if( data.error ) {
            console.error( data.error );
        }else {
            endpoints = data;
        }
        console.log('*** requestedEndpoints ***', JSON.stringify(data));
    });

    // This listens for Journey Builder to send endpoints
    // Parameter is either the endpoints data or an object with an
    // "error" property containing the error message
    connection.on('getEndpoints', function( data ) {
        if( data.error ) {
            console.error( data.error );
        } else {
            endpoints = data;
        }
        console.log('*** getEndpoints ***', data);
    });

});
