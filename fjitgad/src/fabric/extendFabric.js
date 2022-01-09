import { fabric } from "fabric";

fabric.Canvas.prototype.sequence = {
    prefix: 'iotc',
    seq: 0,
    set: function (p, type) {
        if (type === "prefix")
            this.prefix = String(p);
        else if (type === "seq")
            this.seq = p;
    },
    get: function () {
        this.seq += 1;
        return this.prefix + this.seq;
    }
};
fabric.Object.prototype.getZIndex = function () {
    return this.canvas.getObjects().indexOf(this);
};
fabric.Canvas.prototype.addToPosition = function (object, position) {
    this.add(object);
    while (object.getZIndex() > position) {
        this.sendBackwards(object);
    }
};


fabric.Canvas.prototype.add = (function (originalFn) {
    return function (...args) {
        originalFn.call(this, ...args);
        args.forEach(object => {
            if (!object.uid)
                object.uid = this.sequence.get();
        });
        return this;
    };
})(fabric.Canvas.prototype.add);

export default fabric;