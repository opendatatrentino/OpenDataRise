

(function () {
             var conf = new mockdoc.Config(
                     "ODR MOCKUPS",
                     "Entity Alignment",
                     "0.6.1",
                     "https://github.com/opendatatrentino/OpenDataRise/wiki/Mockups/",
                     "https://github.com/opendatatrentino/OpenDataRise/wiki/deliverables/",
                     "odr-entity-alignment-mockups",
                     "26.nov.2014");

             mockdoc.configure(conf);
             mockdoc.renderMockups();

             $("#left-tabs").tabs();

             $(".has-bz-tooltip").tooltip({
                 // tooltipClass: "???",
                 content: function () {
                     return $("#tooltip-loc-turistiche-bz")[0].outerHTML;
                 }

             });

             $(".has-replace-tooltip").tooltip({
                 // tooltipClass: "???",
                 content: function () {
                     return "Replaces:  <span style='font-style:italic'>" + this.title + "</span>  <br/>"
                             + $("#tooltip-loc-turistiche-bz")[0].outerHTML;
                 }

             });

         })();