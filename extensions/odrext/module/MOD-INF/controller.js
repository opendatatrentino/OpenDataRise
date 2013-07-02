/*global Packages */

var html = "text/html";
var encoding = "UTF-8";
var ClientSideResourceManager = Packages.com.google.refine.ClientSideResourceManager;
var logger = Packages.org.slf4j.LoggerFactory.getLogger("odrext");

/*
 * Function invoked to initialize the extension.
 */
function init() {
    logger.info("Initializing...");
    
    
    logger.info("Finished initializing.");
}

/*
 * Function invoked to handle each request in a custom way.
 */


function process(path, request, response) {
    // Analyze path and handle this request yourself.

    if (path === "/" || path === "") {
/*        var context = {};
        // here's how to pass things into the .vt templates
        context.someList = ["Superior", "Michigan", "Huron", "Erie", "Ontario"];
        context.someString = "foo";
        context.someInt = Packages.eu.trentorise.opendatarise.prove.ProvaDeps.provaJersey("ciao"); // Packages.com.google.refine.sampleExtension.SampleUtil.stringArrayLength(context.someList);
*/
        send(request, response, "index.vt", context);
    }
}

function send(request, response, template, context) {
    butterfly.sendTextFromTemplate(request, response, context, template, encoding, html);
}



