package naga.core.spi.json.teavm;

import naga.core.json.JsonArray;
import naga.core.json.WritableJsonObject;
import org.teavm.jso.JSObject;
import org.teavm.jso.core.JSBoolean;
import org.teavm.jso.core.JSNumber;

/**
 * Client-side implementation of JsonObject interface.
 *
 * @author Bruno Salmon
 */
public final class TeaVmJsonObject extends TeaVmJsonElement implements WritableJsonObject {

    public static TeaVmJsonObject create(JSObject jso) {
        if (jso == null || JSUtil.isUndefined(jso))
            return null;
        return new TeaVmJsonObject(jso);
    }

    public TeaVmJsonObject() {
        this(JSUtil.newJSObject());
    }

    public TeaVmJsonObject(JSObject jso) {
        super(jso);
    }


    @Override
    public JSObject getNativeElement(String key) {
        return JSUtil.getJSValue(jsValue, key);
    }

    @Override
    public double getDouble(String key) {
        return JSUtil.js2Double(getNativeElement(key));
    }


    @Override
    public String getString(String key) {
        return JSUtil.js2String(getNativeElement(key));
    }

    @Override
    public native boolean has(String key) /*-{
        return key in this;
    }-*/;

    @Override
    public JsonArray keys() {
        return TeaVmJsonArray.create(JSUtil.getKeys(jsValue));
    }

    @Override
    public <T> T remove(String key) {
        JSUtil.deleteJSValue(jsValue, key);
        return null;
    } /*-{
        toRtn = this[key];
        delete this[key];
        return toRtn;
    }-*/;

    @Override
    public void setNativeElement(String key, Object element) {
        JSUtil.setJSValue(jsValue, key, (JSObject) element);
    }

    @Override
    public void set(String key, boolean bool) {
        setNativeElement(key, JSBoolean.valueOf(bool));
    }

    @Override
    public void set(String key, double number) {
        setNativeElement(key, JSNumber.valueOf(number));
    }

    /**
     * Returns the size of the map (the number of keys).
     * <p>
     * <p>NB: This method is currently O(N) because it iterates over all keys.
     *
     * @return the size of the map.
     */
    @Override
    public final native int size() /*-{
        size = 0;
        for (key in this) {
          if (Object.prototype.hasOwnProperty.call(this, key)) {
            size++;
          }
        }
        return size;
    }-*/;
}