/*global ODRDICT */
// This file is added to the /project page




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
     * @param {Number} step
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
     * 
     * @returns {undefined}
     */
    updateUI : function(onDone){
        console.log("odr: updateUI");        
        var po = theProject.overlayModels.OdrProjectOverlay;
        $("#odr-step-title").text(ODRDICT.STEP + " " + po.step + ": " + ODRDICT.STEPS[po.step].NAME);
        $("#odr-nav-help-text").text(ODRDICT.STEPS[po.step].DESCRIPTION);
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
        
        switch (po.step) {
            case 1: 
                break;
            case 2: 
                break;                
            case 3: 
                break;                
            case 4: 
                break;                
            case 5: 
                break;                
            case 6: 
                break;                
            case 7: 
                break;                
            case 8: 
                
                break;                            
        }
        
        if (onDone) {
            onDone();
        }
    }
        
    
};





