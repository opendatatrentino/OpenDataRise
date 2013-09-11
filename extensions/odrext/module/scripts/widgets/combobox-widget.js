;
(function($) {
    if (!$.suggest) {
        alert("$.suggest required");
    }

    $.suggest("suggestcatalog",
            $.extend(
            true,
            {},
            $.suggest.suggest.prototype,
            {
                shift_enter: function(e) {
                  // odr let it work anyway if (this.options.suggest_new) {
                    console.log("entering suggestcatalog.shift_enter(e)");
                    this.suggest_new();
                    this.hide_all();
                  //}
                },   
                // I patched suggest js for having this
                focusout : function(e){
                    console.log("entering suggestcatalog.focusout(e)");
                    if (this.toUpdateWhenFocusOut){
                        this.enter(e);
                    }
                    
                },
                enter: function(e) {
                    var o = this.options,
                            visible = this.pane.is(":visible");
                    
                    if (visible) {
                        console.log("entering suggestcatalog.enter(e)");
                        console.log("o = ", o);
                        if (e.shiftKey) {
                            console.log("entering suggestcatalog.enter(e) shiftKey");
                            this.shift_enter(e);
                            e.preventDefault();
                            return;
                        }
                        else if ($("." + o.css.item, this.list).length) {
                            console.log("entering suggestcatalog.enter(e) $('.' + o.css.item, this.list).length");
                            var s = this.get_selected();
                            if (s) {
                                console.log("entering suggestcatalog.enter(e) if (s)");
                                this.onselect(s);
                                this.hide_all();
                                e.preventDefault();
                                return;
                            }
                            else if (!o.soft) {
                                console.log("entering suggestcatalog.enter(e) !o.soft");
                                
                                var data = this.input.data("data.suggest");
                                if ($("." + this.options.css.item + ":visible", this.list).length) {
                                    this.updown(false);
                                    e.preventDefault();
                                    return;
                                }
                            } 
                        }
                    }
                    if (o.soft) {
                        // submit form
                        this.soft_enter();
                    }
                    else {
                        
                        console.log("entering suggestcatalog.enter(e) last case");
                        this.shift_enter(e);
                        e.preventDefault();
                        return;                        
                    }
                },
                create_item: function(data, response_data) {
                    var css = this.options.css;

                    var li = $("<li>").addClass(css.item);
                    var name = $("<div>").addClass(css.item_name)
                            .append($("<label>")
                            .append($.suggest.strongify(data.name, response_data.prefix)));
                    // this converts html escaped strings like "&amp;"
                    // back to "&"
                    data.name = name.text();
                    li.append(name);
                    //odr name.prepend($("<div>").addClass(css.item_type).text(data.id));
                    //TODO very smelly hack to disable cache
                    $.suggest.cache = {};
                    return li;
                },
                request: function(value, cursor) {
                    var self = this,
                            o = this.options;

                    var data = {};
                    var query = value;
                    data[o.query_param_name] = query;

                    clearTimeout(this.request.timeout);

                    // odr comment mmm we pass the prefix here but in the the rdf-extension server the prefix is returned back
                    data["prefix"] = query;
                    /* odr start not needed
                     data["type_strict"] = "classes";
                     data["type"] = theProject.id;
                     */

                    var url = o.service_url + o.service_path + "?" + $.param(data, true);
                    var ajax_options = {
                        url: o.service_url + o.service_path,
                        data: data,
                        traditional: true,
                        error: function(xhr) {
                            self.status_error();
                            self.trackEvent(self.name, "request", "error", {
                                url: this.url,
                                response: xhr ? xhr.responseText : ''
                            });
                            self.input.trigger("fb-error", Array.prototype.slice.call(arguments));
                        },
                        complete: function(xhr) {
                            if (xhr) {
                                self.trackEvent(self.name, "request", "tid",
                                        xhr.getResponseHeader("X-Metaweb-TID"));
                            }
                        },
                        success: function(data) {
                            $.suggest.cache[url] = data;
                            data.prefix = value;  // keep track of prefix to match up response with input value
                            self.response(data, cursor ? cursor : -1);
                        },
                        dataType: "json",
                        cache: true
                    };
                    this.request.timeout = setTimeout(function() {
                        $.ajax(ajax_options);
                    }, o.xhr_delay);

                },
                onselect: function($selected, focus) {
                  var data = $selected.data("data.suggest");
                  this.toUpdateWhenFocusOut = false;                  
                  if (data) {
                    this.input.val(data.name)
                      .data("data.suggest", data)
                      .trigger("fb-select", data);

                    this.trackEvent(this.name, "fb-select", "index",
                    $selected.prevAll().length);
                  }

                },
                textchange: function() {
                  this.toUpdateWhenFocusOut = true;  
                  this.input.removeData("data.suggest");
                  this.input.trigger("fb-textchange", this);
                  var v = this.input.val();
                  if (v === "") {
                    this.status_start();
                    return;
                  }
                  else {
                    this.status_loading();
                  }
                  this.request(v);
                },                        

                suggest_new: function(e) {
                  var v = this.input.val();
                  v = $.trim(v);
/*                  if (v === "") {
                    return;
                  } 
*/                    
                  if (!v.startsWith("http://") & v !== ""){
                      v = "http://" + v;
                  }
                  this.input.val(v);
                  
                  //console.log("suggest_new", v);
/*                  if (v !== this.lastEnteredValue){
                    this.input
                      .data("data.suggest", v)  // what is this data.suggest??
                      .trigger("fb-select-new", v);
                  } */
                    
                  this.toUpdateWhenFocusOut = false;                    
                  this.input
                    .data("data.suggest", v)
                    .trigger("fb-select-new", v);                  
                  this.trackEvent(this.name, "fb-select-new", "index", "new");
                  this.hide_all();
                }
                        

            }));

    $.extend($.suggest.suggestcatalog, {
        defaults: $.extend(
                true,
                {},
                $.suggest.suggest.defaults,
                {
                    service_url: "",
                    service_path: "command/odrext/suggest-catalog",
                    flyout_service_path: "command/odrext/suggest-catalog",
                    type_strict: "classes",
                    //suggest_new: "Add it",                    
                    cache: false,
                    // odr start
                    flyout: false,
                    //query_param_name: "ckanUrl",
                    // odr end

                    //             soft:true,
                    status: [
                      'Start typing to get suggestions...',
                      'Searching...',
                      '',
                      'Sorry, something went wrong. Please try again later'
                    ],                    
                    nomatch: {
                        title: 'Press enter to start using the catalog.', //'No suggested matches. (Shift + Enter) to add it',
                        heading: null,
                        tips: null
                    }
                })
    });

})(jQuery);
