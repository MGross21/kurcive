# KURCIVE
_Pronounced "Cursive"_

> [!Warning]
> Currently Under Development

## Modular Design

The following diagram presents a simplified system architecture overview, emphasizing core modular components and their data/control flow relationships.

```mermaid
graph TD
    Motors[Motors]
    Servos[Servos]

    Pathing[Pathing]
    Base[Drive Base]
    Slides[Slides]

    Arms[Arms]
    Motion[Motion]
    Bot[Bot]
    Virtualization[Virtualization]
    OpModes[OpModes]

    Pathing --> Motion

    Motors --> Base
    Motors --> Slides
    Motors --> Arms

    Servos --> Slides
    Servos --> Arms

    Base --> Motion
    Arms --> Motion

    Motion --> Bot
    Bot --> Virtualization
    Bot --> OpModes
```
