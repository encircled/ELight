ELight
======

Lightweight IoC container for java.
Full reference implementation of JSR-330 and custom features.

Usage
=======

## New context based on annotation configuration
new AnnotationApplicationContext("com.example.package").initialize();

## JSR 330 support
- @Inject
- @Singleton
- @Named
- @Qualifier
- @Provider

## Custom supported features
- Injection via annotation with additional parameters (cz.encircled.elight.core.Wired)
- Configurable order for array and collection injection (cz.encircled.elight.core.Order)
- Conditional including of components to context (cz.encircled.elight.core.Conditional)
- Instance creating delegating to your custom factory-class (cz.encircled.elight.core.Creator)
- Post/Pre processors for custom component configuration (cz.encircled.elight.core.ComponentPostProcessor)
- PostConstruct/PreDestroy methods
- Adding pre-resolved dependencies to context