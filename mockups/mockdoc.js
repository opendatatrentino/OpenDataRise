

mockdoc = {
    _config : null,
    
    /**
     * @param {string} project i.e. "MyApp Mocks" 
     * @param {string} topic i.e. "Admin UI"
     * @param {string} version i.e. "0.0.0"
     * @param {string} siteUrl i.e. "http://myproject/docs/"
     * @param {string} pdfsUrl i.e. "pdf/"
     * @param {string} pdfFilename filename without the extension, i.e. "file-name"
     * @param {string} date i.e. "01.jan.1970" 
     * @constructor 
     */
    Config : function(project, topic, version, siteUrl, pdfsUrl, pdfFilename, date){
        if (!project){
            throw new Error("project must contain something!");
        }
        if (!topic){
            throw new Error("topic must contain something!");
        }
        if (!version){
            throw new Error("version must contain something!");
        }
        if (!siteUrl){
            throw new Error("siteUrl must contain something!");
        }
        if (!pdfsUrl){
            throw new Error("pdfUrl must contain something!");
        }
        if (!pdfFilename){
            throw new Error("pdfFileName must contain something!");
        }
        if (!date){
            throw new Error("date must contain something!");
        }
        this.project = project;
        this.topic = topic;
        this.version = version; 
        this.siteUrl = siteUrl;
        this.pdfsUrl = pdfsUrl;
        this.pdfFilename = pdfFilename;
        this.date = date; 
    },
    
    /** 
     * @return {string} 
     */
    pdfUrl : function(){
        var c = mockdoc._config; 
        return c.pdfsUrl + c.pdfFilename + "-" + c.date + "-" + c.version + ".pdf";
    },
            
    /**
     * @param {mockdoc.Config} config     
     */
    configure : function(config){
        if (!config){
            throw new Error("Invalid config: " + config);
        }
        this._config = config;
    },
    
    /** 
     * Remember to configure mockdoc accordingly before calling this function 
     */
    renderMockups: function () {
        
        if (!mockdoc._config){
            throw new Error("Mockdoc is not configured yet. Run mockdoc.configure() to do it!");
        }
        
        $("#mockdoc-title").text(mockdoc._config.project + " - " + mockdoc._config.topic);
        $("#mockdoc-project").text(mockdoc._config.project);
        $("#mockdoc-topic").text(mockdoc._config.topic + " " + mockdoc._config.version);
        
        $("#mockdoc-pdflink").prop("href", mockdoc.pdfUrl());
        
        $(".mockdoc-note").tooltip(
                {
                    content: function () {
                        return this.title;
                    }
                }

        );

        $(".mockdoc-note").each(function (i, el) {
            var tooltipHtml = "<span style='padding-left:10px'> " + $(el).text() + " </span>" + $(el).tooltip("option", "content").apply(el);
            $('<div>')
                    .html(tooltipHtml)
                    .appendTo($("#mockdoc-circle-descr"));

        });

        $("#mockdoc-instructions").prop("href", mockdoc.pdfUrl + mockdoc.pdfFilename + "-" + mockdoc.date + "-" + mockdoc.version + ".pdf");

    }
};