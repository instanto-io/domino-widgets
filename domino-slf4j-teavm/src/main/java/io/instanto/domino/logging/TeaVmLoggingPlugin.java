package io.instanto.domino.logging;

import org.teavm.model.ClassHolderTransformer;
import org.teavm.model.MethodHolder;
import org.teavm.model.util.ModelUtils;
import org.teavm.vm.spi.TeaVMHost;
import org.teavm.vm.spi.TeaVMPlugin;

/** Installs the browser provider without shipping duplicate org.slf4j classes. */
public final class TeaVmLoggingPlugin implements TeaVMPlugin {
  @Override
  public void install(TeaVMHost host) {
    if (host.getClassLoader().getResource("org/teavm/extras/slf4j/Slf4jPlugin.class") != null) {
      throw new IllegalStateException(
          "Remove teavm-extras-slf4j from application and TeaVM plugin dependencies "
              + "when using domino-slf4j-teavm; only one logging bridge may be installed.");
    }
    host.add(
        (ClassHolderTransformer)
            (cls, context) -> {
              if (!cls.getName().equals("org.slf4j.LoggerFactory")) return;
              var replacement =
                  context.getHierarchy().getClassSource().get(TeaVmLoggerFactory.class.getName());
              // Wrapper methods reference a separate holder, so all entry points share one
              // provider.
              for (var field : cls.getFields().toArray(org.teavm.model.FieldHolder[]::new)) {
                cls.removeField(field);
              }
              for (var method : cls.getMethods().toArray(MethodHolder[]::new)) {
                cls.removeMethod(method);
              }
              for (var method : replacement.getMethods()) {
                cls.addMethod(ModelUtils.copyMethod(method));
              }
            });
  }
}
