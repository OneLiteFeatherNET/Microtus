package net.minestom.codegen.feature;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.squareup.javapoet.*;
import net.minestom.codegen.MinestomCodeGenerator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.lang.model.element.Modifier;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class FeatureFlagGenerator extends MinestomCodeGenerator {

    private static final String FEATURE_FLAG_FILE_PACKAGE = "net.minestom.server.feature"; // Microtus - Feature flag
    private static final String CLASS_NAME = "FeatureFlags";
    private static final String FEATURE_KEY = "feature";
    private static final Logger LOGGER = LoggerFactory.getLogger(FeatureFlagGenerator.class);

    private final InputStream featureFlagFile;
    private final File outputFolder;

    /**
     * Creates a new code generator.
     *
     * @param packageName the package name of the generated class
     */
    public FeatureFlagGenerator(InputStream featureFlagFile, File outputFolder) {
        super(FEATURE_FLAG_FILE_PACKAGE);
        this.featureFlagFile = featureFlagFile;
        this.outputFolder = outputFolder;
    }

    @Override
    public void generate() {
        if (featureFlagFile == null) {
            LOGGER.error("Failed to find feature_flag.json.");
            LOGGER.error("Stopped code generation for feature_flag.json.");
            return;
        }
        if (!outputFolder.exists() && !outputFolder.mkdirs()) {
            LOGGER.error("Output folder for code generation does not exist and could not be created.");
            return;
        }
        JsonElement featureFlags = GSON.fromJson(new InputStreamReader(featureFlagFile), JsonElement.class);

        ClassName featureFlag = ClassName.get(FEATURE_FLAG_FILE_PACKAGE, CLASS_NAME);
        ClassName namespacedId = ClassName.get("net.minestom.server.utils", "NamespaceID");

        TypeSpec.Builder featureFlagEnum = TypeSpec.enumBuilder(featureFlag)
                .addModifiers(Modifier.PUBLIC).addJavadoc("AUTOGENERATED by " + getClass().getSimpleName());

        // Fields
        featureFlagEnum.addFields(
                List.of(
                        FieldSpec.builder(namespacedId, FEATURE_KEY, Modifier.PRIVATE, Modifier.FINAL).build(),
                        FieldSpec.builder(ArrayTypeName.of(featureFlag), "VALUES", Modifier.PRIVATE, Modifier.STATIC, Modifier.FINAL).initializer(CLASS_NAME + ".values()").build()
                )
        );

        // Methods
        featureFlagEnum.addMethods(
                List.of(
                        // Constructor
                        MethodSpec.constructorBuilder()
                                .addParameter(
                                        ParameterSpec.builder(namespacedId, FEATURE_KEY).addAnnotation(NotNull.class).build()
                                )
                                .addStatement("this.$1L = $1L", FEATURE_KEY)
                                .build(),
                        MethodSpec.methodBuilder(FEATURE_KEY)
                                .addModifiers(Modifier.PUBLIC)
                                .returns(namespacedId.annotated(AnnotationSpec.builder(NotNull.class).build()))
                                .addStatement("return this.$L", FEATURE_KEY)
                                .build(),
                        MethodSpec.methodBuilder("getValue")
                                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                                .addParameter(ParameterSpec.builder(TypeName.INT, "id").build())
                                .addAnnotation(Nullable.class)
                                .returns(ClassName.get(FEATURE_FLAG_FILE_PACKAGE, CLASS_NAME))
                                .addCode("return VALUES[$L];", "id")
                                .build()
                )
        );
        featureFlags.getAsJsonArray().forEach(featureFlagElement -> {
            String object = featureFlagElement.getAsString();
            featureFlagEnum.addEnumConstant(extractNamespace(object), TypeSpec.anonymousClassBuilder(
                            "$T.from($S)",
                    namespacedId, object
                    ).build()
            );
        });
        writeFiles(
                List.of(
                        JavaFile.builder(FEATURE_FLAG_FILE_PACKAGE, featureFlagEnum.build())
                                .indent(DEFAULT_INDENT)
                                .skipJavaLangImports(true)
                                .build()
                ),
                outputFolder
        );
    }
}