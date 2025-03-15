package seedu.address.model.person;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Module Registry enum that stores all preset modules.
 * The Module record is nested inside this enum to restrict its instantiation.
 */
public enum ModuleRegistry {

    CS1231S(new Module("CS1231S", "Discrete Structures")),
    CS2030S(new Module("CS2030S", "Programming Methodology II")),
    CS2040S(new Module("CS2040S", "Data Structures and Algorithms")),
    CS2100(new Module("CS2100", "Computer Organisation")),
    CS2103T(new Module("CS2103T", "Software Engineering")),
    CS2106(new Module("CS2106", "Introduction to Operating Systems")),
    CS2109S(new Module("CS2109S", "Introduction to AI and Machine Learning")),
    CS3230(new Module("CS3230", "Design and Analysis of Algorithms")),
    CS2101(new Module("CS2101", "Effective Communication for Computing Professionals"));
    public static final String MESSAGE_UNREGISTERED_MODULE = "input module is not registered.";
    private final Module module;

    ModuleRegistry(Module module) {
        this.module = module;
    }

    public Module getModule() {
        return module;
    }

    public static Module getModuleByCode(String code) {
        for (ModuleRegistry entry : ModuleRegistry.values()) {
            if (entry.module.getModuleCode().equalsIgnoreCase(code)) {
                return entry.module;
            }
        }
        return null;
    }

    public static List<Module> getAllModules() {
        List<Module> modules = new ArrayList<>();
        for (ModuleRegistry entry : ModuleRegistry.values()) {
            modules.add(entry.module);
        }
        return Collections.unmodifiableList(modules);
    }

    /**
     * Represents a Module that stores the module code and name.
     * This class is immutable and can only be instantiated by ModuleRegistry.
     */
    public static class Module {
        private final String moduleCode; // Unique identifier for the module (e.g., "CS1010")
        private final String moduleName; // Full name of the module (e.g., "Programming Methodology")

        /**
         * Private constructor to restrict instantiation to ModuleRegistry only.
         *
         * @param moduleCode The unique code of the module.
         * @param moduleName The name of the module.
         */
        private Module(String moduleCode, String moduleName) {
            this.moduleCode = moduleCode;
            this.moduleName = moduleName;
        }

        public String getModuleCode() {
            return this.moduleCode;
        }

        public String getModuleName() {
            return this.moduleName;
        }

        @Override
        public String toString() {
            return this.moduleCode + " " + this.moduleName;
        }
    }
}
