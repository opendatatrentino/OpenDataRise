/*global ODRDICT,$ */
// This file is added to the /project page


//Internationalization init
var lang = navigator.language.split("-")[0]
		|| navigator.userLanguage.split("-")[0];
var dictionary = "";
$.ajax({
	url : "/command/core/load-language?",
	type : "POST",
	async : false,
	data : {
	  module : "odrext"
//		lang : lang
	},
	success : function(data) {
		dictionary = data;
	}
});
$.i18n.setDictionary(dictionary);
// End internationalization



/* 
 * OpenDataRise
 * 
 */
var ODR = {
    MAX_STEPS : 8,
    /**
     * 
     * @param {function} f
     * @param {type} s
     * @returns {undefined}
     */
    notImplemented : function(f, s){
        // todo p1 dav find a decent way to show the error in the UI
        // f.name should work in Firefox and Chrome
        var m = "Function " + f.name + " is not implemented";
        if (s){
            m = s;
        }                
        throw new ODR.Exception(m);
    },
    
    Exception : function(){
        var i;
        var arr = [];
        arr.push("ODR.Exception: ");
        for (i=0; i<arguments.length; i++){
            arr.push(arguments[i]);
        }
        console.error.apply(null, arguments);
        throw new Error();
    },
    
    /**
     * @param {number} step
     */
    setStep : function(step){        
        
        if (step < 2 || step > ODR.MAX_STEPS) {
            throw new ODR.Exception("Step number out of rage: ", step);
        }
        console.log("Switching to step ",step);

        
        Refine.postProcess(
                        "odrext",
                        "set-step",
                        {},
                        { step: step },
                        {   everythingChanged: true,
                            stepChanged : true}, // todo p3 might be 'everything' is too much 
                        {   
                                onFinallyDone: function() {
                                        console.log("odr: setStep - onFinallyDone.");

                                }
                        }
        );
        
        
    },

    /**
     * 
     * @returns {undefined}
     */
    initUI : function(){        
        console.log("odr: InitUI");        
        $("#odr-nav-prev-button").click(function(evt) {
                                            var po = theProject.overlayModels.OdrProjectOverlay;
                                            ODR.setStep(po.step - 1);
                                            evt.preventDefault();
                                            return false;
                                          });
        $("#odr-nav-next-button").click(function(evt) {
                                            var po = theProject.overlayModels.OdrProjectOverlay;
                                            ODR.setStep(po.step + 1);
                                            evt.preventDefault();
                                            return false;
                                          });
        ODR.updateUI();
    },

    /**
     * Fills an helpbox with string taken from the language dictionary
     * @param {string} id id of the helpbox to fill in. ie 'myHelpbox'
     * @param {number} nElements number of elements to fill   
     * @param {string} dict Dictionary where to find helpbox contents 
     */
    fillHelpBox : function(id, dict, nElements){
        var helpbox = $("#"+id);
        var i;
        var prefix;
        var descr;
        console.log("Filling helpbox: ", id, " with dict ", dict);
        
        helpbox.empty();
        for (i=1;i<=nElements;i++){
            prefix = "help-"+i+"-";
            descr = $.i18n._(dict)[prefix+"descr"];
            if (!descr) {
                descr = "";
            }
/*            helpbox.append("<h3 class='helpbox'><div><a href='#'>"+$.i18n._(dict)[prefix+"title"]
                    +"</a></div></h3><div> <p>"
                    + descr +"</p></div>");
*/            
            helpbox.append("<h3 class='helpbox'> " +
"<table border='0'  cellpadding='3' cellspacing='3'>" + 
	"<tr>" +
	"	<td ><a class='helpbox-triangle' style='font-size:8px; margin-right:5px;'>▶</a></td>"+
		"<td><a href='#'>"+$.i18n._(dict)[prefix+"title"]+"</a></td>"+
	"</tr>"+
"</table></h3>" + 

"<p>" +descr +"</p></div>");

            
            
        }         
        helpbox.togglepanels();
    },
            
    /**
     * @return {boolean}
     */
    isHorizontallyReconciled : function(){             
        return (theProject.overlayModels.OdrProjectOverlay.rowModelTotal === theProject.overlayModels.OdrProjectOverlay.horizontallyReconciledRows);
    },

        
            
    /**
     * 
     * @returns {undefined}
     */
    updateUI : function(onDone){
        console.log("odr: updateUI");        
        var po = theProject.overlayModels.OdrProjectOverlay;
        

        
        if (po.step < 3){
            $("#odr-nav-prev-button").hide();
            $("#odr-nav-next-button").show();            
        } else
        if (po.step < ODR.MAX_STEPS){
            $("#odr-nav-prev-button").show();
            $("#odr-nav-next-button").show();
        } else {
            $("#odr-nav-prev-button").show();
            $("#odr-nav-next-button").hide();            
        }      
        
        if (po.step > 1){
            $("#odr-step-title").text($.i18n._("navigation")["step"] + " " + po.step +": " + $.i18n._("step-"+po.step)["name"]) ;
        }
        
        switch (po.step) {
            case 1:                 
                break;
            case 2: 
                ODR.fillHelpBox("odr-nav-helpbox", "step-" + po.step, 3);
                break;                
            case 3: 
                ODR.fillHelpBox("odr-nav-helpbox", "step-" + po.step, 3);
                break;                
            case 4: 
                ODR.fillHelpBox("odr-nav-helpbox", "step-" + po.step, 3);
                break;                
            case 5: 
                if (ODR.isHorizontallyReconciled()){
                    console.log("Completely horizontally reconciled.");
                    ODR.fillHelpBox("odr-nav-helpbox", "step-5", 3);
                } else {
                    console.log("Not completely horizontally reconciled yet");
                    ODR.fillHelpBox("odr-nav-helpbox", "step-5-automatic-reconcile", 4);
                }
                break;                
            case 6: 
                ODR.fillHelpBox("odr-nav-helpbox", "step-" + po.step, 3);
                break;                
            case 7: 
                ODR.fillHelpBox("odr-nav-helpbox", "step-" + po.step, 4);
                break;                
            case 8: 
                ODR.fillHelpBox("odr-nav-helpbox", "step-" + po.step, 1);
                break;             
        }
        
        $("#odr-nav-more-help").text($.i18n._("navigation")["more-help"]);
        // todo  fix the more help link  see https://github.com/opendatatrentino/OpenDataRise/issues/26          
                          
        
        
        if (onDone) {
            onDone();
        }
    }
        
    
};





