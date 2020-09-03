// Generated by WebFx

module webfx.demos.customcontrol.application {

    // Direct dependencies modules
    requires java.base;
    requires javafx.base;
    requires javafx.controls;
    requires javafx.graphics;
    requires webfx.platform.shared.util;

    // Exported packages
    exports webfx.demos.customcontrol;
    exports webfx.demos.customcontrol.clock;
    exports webfx.demos.customcontrol.clock.emul;
    exports webfx.demos.customcontrol.clock.events;
    exports webfx.demos.customcontrol.clock.skins;
    exports webfx.demos.customcontrol.clock.tools;

    // Provided services
    provides javafx.application.Application with webfx.demos.customcontrol.CustomControlApplication;

}