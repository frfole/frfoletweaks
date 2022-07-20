package code.frfole.frfoletweaks.util;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.templates.TaggedChoice;
import com.mojang.datafixers.types.templates.TypeTemplate;
import com.mojang.serialization.Dynamic;
import net.minecraft.SharedConstants;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;

public final class FakeDataFixer implements com.mojang.datafixers.DataFixer {
    private static final class FakeSchema extends Schema {
        public FakeSchema() {
            super(SharedConstants.getGameVersion().getWorldVersion(), null);
        }

        @Override
        protected Map<String, Type<?>> buildTypes() {
            return Map.of();
        }

        @Override
        public Set<String> types() {
            return Set.of();
        }

        @Override
        public Type<?> getTypeRaw(DSL.TypeReference type) {
            return null;
        }

        @Override
        public Type<?> getType(DSL.TypeReference type) {
            return null;
        }

        @Override
        public TypeTemplate resolveTemplate(String name) {
            return null;
        }

        @Override
        public TypeTemplate id(String name) {
            return null;
        }

        @Override
        protected TypeTemplate getTemplate(String name) {
            return null;
        }

        @Override
        public Type<?> getChoiceType(DSL.TypeReference type, String choiceName) {
            return null;
        }

        @Override
        public TaggedChoice.TaggedChoiceType<?> findChoiceType(DSL.TypeReference type) {
            return null;
        }

        @Override
        public void registerTypes(Schema schema, Map<String, Supplier<TypeTemplate>> entityTypes, Map<String, Supplier<TypeTemplate>> blockEntityTypes) {
        }

        @Override
        public Map<String, Supplier<TypeTemplate>> registerEntities(Schema schema) {
            return Map.of();
        }

        @Override
        public Map<String, Supplier<TypeTemplate>> registerBlockEntities(Schema schema) {
            return Map.of();
        }

        @Override
        public void registerSimple(Map<String, Supplier<TypeTemplate>> map, String name) {
        }

        @Override
        public void register(Map<String, Supplier<TypeTemplate>> map, String name, Function<String, TypeTemplate> template) {
        }

        @Override
        public void register(Map<String, Supplier<TypeTemplate>> map, String name, Supplier<TypeTemplate> template) {
        }

        @Override
        public void registerType(boolean recursive, DSL.TypeReference type, Supplier<TypeTemplate> template) {
        }
    }

    public static final FakeSchema schema = new FakeSchema();

    @Override
    public <T> Dynamic<T> update(DSL.TypeReference type, Dynamic<T> input, int version, int newVersion) {
        return input;
    }

    @Override
    public Schema getSchema(int key) {
        return schema;
    }
}
