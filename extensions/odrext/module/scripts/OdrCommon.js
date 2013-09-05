//Internationalization init
var lang = navigator.language.split("-")[0]
        || navigator.userLanguage.split("-")[0];
var dictionary = "";
$.ajax({
    url: "/command/core/load-language?",
    type: "POST",
    async: false,
    data: {
        module: "odrext",
//		lang : lang
    },
    success: function(data) {
        dictionary = data;
    }
});
$.i18n.setDictionary(dictionary);
// End internationalization



var Ckanalyze = {
    Types: {
        DATE: "DATE",
        EMPTY: "EMPTY",
        FLOAT: "FLOAT",
        GEOJSON: "GEOJSON",
        INT: "INT",
        STRING: "STRING"
    }

}

var OdrCommon = {
    /**
     * @param {string} message
     * @return {string} the HTML as string to put in a waiting dialog 
     */
    waitingHtml: function(message) {
        return "<img src='/images/large-spinner.gif'/> <span>" + message + "....</span>";
    },
    /**
     * Two digit format by default
     * @param {number} num
     * @return {string}
     */
    formatDouble: function(num) {
        return Globalize.format(num, "n2");
    },
    /**      
     * Formats to one decimal digit. Returns "-" if number is less than 0.01
     * @param {number} num
     * @return {string}
     */
    formatAvgStringLength: function(num) {
        if (num < 0.01) {
            return "-"
        } else {
            return Globalize.format(num, "n1");
        }
    },
    /**
     * One digit format by default
     * @param {number} num
     * @return {string}
     */
    formatPercentage: function(num) {
        return Globalize.format(num, "n1");
    },
    /**
     * One digit format by default. If type not present returns "-"
     * @param {Ckanalyze.Types} type
     * @param {Object} typeMap
     * @param {number} totalColsCount
     * @return {string}
     */
    formatTypePercentage: function(type, typeMap, totalColsCount) {
        if (type in typeMap) {
            return Globalize.format(typeMap[type] / totalColsCount, "p1");
        } else
            return "0";

    },
    /**     
     * @param {number} num integer number
     * @return {string}
     */
    formatInteger: function(i) {
        return Globalize.format(i, "n0");
    },
            
    /**
     * 
     * @param {number} fileSizeInBytes
     * @returns {string}
     */
    formatFileSize: function(fileSizeInBytes) {

        var i = -1;
        var byteUnits = [' kB', ' MB', ' GB', ' TB', 'PB', 'EB', 'ZB', 'YB'];
        do {
            fileSizeInBytes = fileSizeInBytes / 1024;
            i++;
        } while (fileSizeInBytes > 1024);

        return Math.max(fileSizeInBytes, 0.1).toFixed(1) + byteUnits[i];
    }

}




