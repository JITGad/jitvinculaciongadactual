$.widget("ui.selectmenucolor", $.ui.selectmenu, {
    _renderButtonItem: function (item) {
        var hexcode = $(item.element).data('value');
        var buttonItem = $("<span>", {
            "class": "ui-selectmenu-text"
        });
        var inputcolor = $('<div>', {
            "class": "form-control form-control-color",
            "disabled": true
        });
        buttonItem.css("display", "flex");
        
        inputcolor.css("background-color", hexcode);

        buttonItem.append(inputcolor);
        buttonItem.append(item.label);

        return buttonItem;
    },
    _renderItem: function (ul, item) {
        var hexcode = $(item.element).data('value');
        var li = $("<li>");
            var wrapper = $("<div>");

        if (item.disabled) {
            li.addClass("ui-state-disabled");
        }

        var inputcolor = $('<div>', {
            "class": "form-control form-control-color",
            "disabled": true
        });
        inputcolor.css("background-color", hexcode);

        wrapper.append(inputcolor);
        wrapper.append(item.label);
        wrapper.css("display", "flex");

        return li.append(wrapper).appendTo(ul);
    }
});