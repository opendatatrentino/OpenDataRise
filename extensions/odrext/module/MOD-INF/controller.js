/*global Packages */

var html = "text/html";
var encoding = "UTF-8";
var ClientSideResourceManager = Packages.com.google.refine.ClientSideResourceManager;
var davProject = Packages.com.google.refine.model.Project;
var PO = Packages.eu.trentorise.opendata.opendatarise.OdrProjectOverlay;
var ODR = Packages.eu.trentorise.opendata.opendatarise.ODR;
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
    RefineServlet.registerCommand(module, "search-ckan", new Packages.eu.trentorise.opendata.opendatarise.commands.SearchCatalogCommand());
    
    // Register importer and exporter
    var IM = Packages.com.google.refine.importing.ImportingManager;

    IM.registerController(
      module,
      "ckan-importing-controller",
      new Packages.eu.trentorise.opendata.opendatarise.importing.CkanImportingController()
    );

    ClientSideResourceManager.addPaths(
            "index/scripts",
            module,
            [
                "externals/jquery.dataTables.min.js",
                "scripts/widgets/combobox-widget.js",
                "scripts/widgets/helpbox-widget.js",
                "scripts/index/ckan-importing-controller.js",
                "scripts/index/ckan-source-ui.js"
                
            ]);


    // Style files to inject into /project page
    ClientSideResourceManager.addPaths(
            "index/styles",
            module,
            [
                "styles/widgets/combobox-widget.less",
                "styles/widgets/helpbox-widget.less",
                "styles/index/ckan-source-ui.less"
            ]
            );

    // Script files to inject into /project page
    ClientSideResourceManager.addPaths(
            "project/scripts",
            module,
            [
                
                "scripts/ODR.js",
                "scripts/widgets/combobox-widget.js",
                "scripts/widgets/helpbox-widget.js"
            ]
            );

    // Style files to inject into /project page
    ClientSideResourceManager.addPaths(
            "project/styles",
            module,
            [
                "styles/project-injection.less",
                "styles/widgets/combobox-widget.less",
                "styles/widgets/helpbox-widget.less"
            ]
            );



    logger.debug("Registering overlay model");
    davProject.registerOverlayModel(
            "OdrProjectOverlay",
            PO);

    logger.info("Finished initializing.");

}

/*
 * Function invoked to handle each request in a custom way.
 */

function process(path, request, response) {
    // Analyze path and handle this request yourself.

    if (path === "/" || path === "") {
        var context = {};

        send(request, response, "index.vt", context);
    }
}


function send(request, response, template, context) {
    butterfly.sendTextFromTemplate(request, response, context, template, encoding, html);
}


