import fabric from "./extendFabric.js";

fabric.Diagram = fabric.util.createClass(fabric.Canvas, {
    initialize: function (elContainer, properties) {
        const options = {
            isDrawingMode: false,
            selection: false,
            stopContextMenu: true
        };

        if (arguments.length === 0 || !elContainer || !properties.observed) {
            throw new Error("Properties undefined to call");
        }

        const el = this._getEl(elContainer);

        this.callSuper('initialize', el, options);

        fabric.util.addListener(fabric.window, 'resize', this._onResizeCanvas.bind(this));

        this.wrapperEl.style.overflow = "hidden";
        this.wrapperEl.style.backgroundColor = "mintcream";

        this._onResizeCanvas();

        this.defaultCursor = "pointer";

    },

    isDragging: false,


    _getEl: function (elContainer) {

        const rootDomNode = document.getElementById(elContainer);

        if (!rootDomNode) {
            throw new Error("Element container is not defined");
        }

        rootDomNode.style.maxHeight = "100%";
        rootDomNode.style.maxWidth = "100%";

        const canvas = document.createElement("canvas");
        let el = `${elContainer}canvas`;

        while (document.getElementById(el) !== null) {
            el += fabric.util.getRandomInt(0, 500);
        }

        canvas.id = el;
        canvas.width = rootDomNode.clientWidth;
        canvas.height = rootDomNode.clientHeight;
        canvas.style.maxHeight = "100%";
        canvas.style.maxWidth = "100%";


        rootDomNode.appendChild(canvas);

        return el;
    },

    getCursor: function () {

        return this.upperCanvasEl.style.cursor;

    },

    _onResizeCanvas: function () {

        this.setWidth(this.wrapperEl.parentNode.clientWidth);
        this.setHeight(this.wrapperEl.parentNode.clientHeight);

        //this.setZoom(0.13);
    },

    setDiagram: function (diagram) {
        this._diagram = diagram;
    },

    _touchStart: function () {

        this.isTouchDevice = true;

    }
});

export { fabric as DiagramaJuego };