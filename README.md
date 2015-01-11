ELight
======

Simple and lightweight IoC container

Usage
=======

## New context based on annotation configuration
new AnnotationApplicationContext("com.example.package").initialize();

## Supported features
- Injection via annotation (cz.encircled.elight.core.Wired)
- Configurable order for collection injection (cz.encircled.elight.core.Order)
- Conditional including in context (cz.encircled.elight.core.Conditional)
- Instance creating delegating to your custom factory (cz.encircled.elight.core.Creator)
- Post/Pre processors for custom component configuration (cz.encircled.elight.core.ComponentPostProcessor)
- PostConstruct/PreDestroy methods
- Adding pre-resolved dependency to context

## JSR 330 support
- @Inject
- @Singleton
- @Named