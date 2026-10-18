package io.instanto.compat;

import elemental2.dom.DomGlobal;
import elemental2.dom.Element;
import org.dominokit.rest.DominoRestConfig;
import org.dominokit.rest.shared.Response;
import org.dominokit.rest.shared.request.*;

public final class DataSmoke {
  public static final String JSON =
      "{\"name\":\"TeaVM \\\"record\\\"\",\"count\":42,\"labels\":[\"one\",\"two\"],\"date\":\"2026-10-18\"}";

  public static Payload roundtrip() {
    var mapper = new Payload_MapperImpl();
    return mapper.read(mapper.write(mapper.read(JSON)));
  }

  public static void main(String[] args) {
    Element result = DomGlobal.document.createElement("section");
    result.id = "data-result";
    DomGlobal.document.body.appendChild(result);
    Payload payload = roundtrip();
    result.setAttribute("data-jackson-name", payload.name());
    result.setAttribute("data-jackson-count", String.valueOf(payload.count()));
    result.setAttribute("data-jackson-labels", String.join(",", payload.labels()));
    result.setAttribute("data-jackson-date", String.valueOf(payload.date().getTime()));
    String base = DomGlobal.document.body.getAttribute("data-service-root");
    DominoRestConfig config =
        DominoRestConfig.initDefaults()
            .setDefaultServiceRoot(base)
            .setDefaultResourceRootPath("service");
    config.addResponseInterceptor(
        new ResponseInterceptor() {
          @Override
          public void onBeforeSuccessCallback(ServerRequest request, Response response) {
            result.setAttribute("data-response-intercepted", "true");
          }
        });
    button(
        "data-get",
        () ->
            PayloadServiceFactory.INSTANCE
                .get("42", "tea time")
                .onSuccess(
                    value -> result.setAttribute("data-get", value.name() + ":" + value.count()))
                .onFailed(
                    failure ->
                        result.setAttribute(
                            "data-error", failure.getStatusCode() + ":" + failure.getBody()))
                .send());
    button(
        "data-post",
        () ->
            PayloadServiceFactory.INSTANCE
                .post(payload)
                .onSuccess(
                    value ->
                        result.setAttribute(
                            "data-post", value.name() + ":" + value.labels().size()))
                .onFailed(
                    failure ->
                        result.setAttribute(
                            "data-error", failure.getStatusCode() + ":" + failure.getBody()))
                .send());
    button(
        "data-fail",
        () ->
            PayloadServiceFactory.INSTANCE
                .missing()
                .onSuccess(value -> result.setAttribute("data-error", "unexpected success"))
                .onFailed(
                    failure ->
                        result.setAttribute(
                            "data-failed-status", String.valueOf(failure.getStatusCode())))
                .send());
    DomGlobal.document.body.setAttribute("data-ready", "true");
  }

  private static void button(String id, Runnable action) {
    Element button = DomGlobal.document.createElement("button");
    button.id = id;
    button.textContent = id;
    button.addEventListener("click", event -> action.run());
    DomGlobal.document.body.appendChild(button);
  }
}
