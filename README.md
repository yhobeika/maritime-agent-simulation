# Maritime Agent Simulation

An agent-based simulation on a 2D grid: pirates search for treasure
chests while coast guards patrol and arrest them, in an environment
whose resources appear, decay and disappear over time.

Course project for UL2IN002 (Object-Oriented Programming), Sorbonne
University, 2026.

![UML class diagram](docs/uml.png)

## Model

**Agents** — `Pirate` moves at 2 cells per step, targets the nearest
chest, and wanders when none is left. `GardeCote` moves at 3 cells per
step, pursues the nearest un-arrested pirate, and patrols once all are
caught. Both inherit `agir()`, dispatched polymorphically each step.

**Resources** — `Coffre` is time-limited: it ages each step and expires
after 8 steps if uncollected. `CourantMarin` weakens by one unit per
step until it dissipates. `Ile` is static and blocks agent spawning.

**Step loop** — every agent's position is validated (throwing
`HorsTerrainException` if off-grid), each agent acts, then evolving
resources are aged and expired ones removed. The run stops early once
every pirate is arrested.

## Design

| Feature | Class |
|---|---|
| Three-level inheritance | `Agent` → `Marin` → `Pirate` / `GardeCote` |
| Abstract class and method | `Agent.agir()` |
| Interface | `Deplacement` |
| Singleton | `Simulation` |
| Custom exception | `HorsTerrainException extends Exception` |
| Static-only utility class | `Utils` (private constructor) |
| Copy constructor | `Coffre` |

Concurrent-modification during the resource sweep is avoided by
collecting expired resources into a temporary list and removing them
after iteration.

## Results

Three configurations, from an 8×10 sea with 2 pirates to a 12×15 sea
with 6 pirates and 3 coast guards:

| Configuration | Steps run | Gold collected | Arrests |
|---|---|---|---|
| 8×10, 2 pirates, 1 guard | 7 (early stop) | 42 | 2 / 2 |
| 10×12, 4 pirates, 2 guards | 20 | 108 | 2 / 4 |
| 12×15, 6 pirates, 3 guards | 25 | 171 | 5 / 6 |

Larger seas favour the pirates: more space means longer pursuits, so
more gold is collected before arrest. Full output in `docs/logs.txt`.

## Running

```bash
cd src
javac *.java
java TestSimulation
```

Note: `Terrain.java` and `Ressource.java` were provided with the
assignment and are not included here. All other classes are my own.
