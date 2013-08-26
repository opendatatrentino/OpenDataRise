/*global Packages */

var html = "text/html";
var encoding = "UTF-8";
var ClientSideResourceManager = Packages.com.google.refine.ClientSideResourceManager;
var davProject = Packages.com.google.refine.model.Project;
var PO = Packages.eu.trentorise.opendata.opendatarise.OdrProjectOverlay;
var ODR = Packages.eu.trentorise.opendata.opendatarise.ODR;
var VelocityHelper = Packages.eu.trentorise.opendata.opendatarise.VelocityHelper;
var Catalogs = Packages.eu.trentorise.opendata.opendatarise.Catalogs;
var logger = ODR.logger;
var OperationRegistry = Packages.com.google.refine.operations.OperationRegistry;
var RefineServlet = Packages.com.google.refine.RefineServlet;


/*
 * Function invoked to initialize the extension.
 */
function init() {

    logger.info("Initializing...");

    // ok I admit I want a Java init and for now didn't find a better way to do it
    ODR.init();

    /*
     *  Operations
     */
    OperationRegistry.registerOperation(module, "set-step", Packages.eu.trentorise.opendata.opendatarise.operations.SetStepOperation);

    /*
     *  Commands
     */
    RefineServlet.registerCommand(module, "set-step", new Packages.eu.trentorise.opendata.opendatarise.commands.SetStepCommand());


    ClientSideResourceManager.addPaths(
            "index/scripts",
            module,
            [
                "scripts/widgets/helpbox-widget.js",
                "scripts/index/ckan-importing-controller.js"
            ]);


    // Style files to inject into /project page
    ClientSideResourceManager.addPaths(
            "index/styles",
            module,
            [
                "styles/widgets/helpbox-widget.less"
            ]
            );

    // Script files to inject into /project page
    ClientSideResourceManager.addPaths(
            "project/scripts",
            module,
            [
                "scripts/ODR.js",
                "scripts/widgets/helpbox-widget.js"                
                
            ]
            );

    // Style files to inject into /project page
    ClientSideResourceManager.addPaths(
            "project/styles",
            module,
            [
                "styles/project-injection.less",
                "styles/widgets/helpbox-widget.less"
            ]
            );


    ClientSideResourceManager.addPaths(
            "index/scripts",
            module,
            [
                "scripts/ODRCKAN.js"
            ]
            );

    ClientSideResourceManager.addPaths(
            "index/styles",
            module,
            [
                "styles/ODRCKAN.less"
            ]
            );



    logger.debug("Registering overlay model");
    davProject.registerOverlayModel(
            "OdrProjectOverlay",
            PO);

    logger.debug("Initalizing Catalogs");
    Catalogs.init();

    // logger.debug(CKANalyzeRawStats.cip);

    logger.info("Finished initializing.");

}

/*
 * Function invoked to handle each request in a custom way.
 */

function process(path, request, response) {
    var context = {};
    // Analyze path and handle this request yourself.

    if (path === "/" || path === "") {


        send(request, response, "index.vt", context);
    } else
        logger.debug("Received request for path: " + path);

    if (path === "ckanalyze-raw.vt") {

        context = {ckanStats: CKANalyzeRawStats.getStats(),
            catalogStats: CKANalyzeRawStats.getStats().getCatalogStats(),
            // Velocity doesn't like static classes. Screw it.
            vh: new VelocityHelper()};

        send(request, response, "ckanalyze-raw.vt", context);
        //send(request, response, "index.vt", context);
    } else {
        send(request, response, path, context);
    }

}


function send(request, response, template, context) {
    butterfly.sendTextFromTemplate(request, response, context, template, encoding, html);
}


