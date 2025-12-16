# Workflow for Stage 3 (Etap 3) - 3-Layer Object Architecture Modeling

## Project Context
- **Project**: Online Cinema (OnlineCinema)
- **Stage 1 (Etap 1)**: Requirements and use case modeling ✅ COMPLETED
- **Stage 2 (Etap 2)**: Use case realization modeling with activity diagrams ✅ COMPLETED
- **Stage 3 (Etap 3)**: 3-layer object architecture modeling ⏳ IN PROGRESS

## Objectives for Stage 3
Based on the instructions in `etap3/IO-L Modelowanie obiektowej 3-warstwowej architektury.pdf`:

### Task 1: Component Diagram (3-Layer Architecture)
**Goal**: Create a component diagram showing the 3-layer architecture (BCE/MVC/MVP)

**Requirements**:
- Define 3 layers as separate components:
  - **Presentation Layer** (View) - UI/GUI integration
  - **Control Layer** (Controller/Presenter) - Business logic coordination
  - **Entity Layer** (Model) - Data processing and database connection
- Connect components using interfaces (lollipop & socket notation)
- NO direct dependencies between components
- Components should be empty (internal structure comes in Task 2)
- Use Facade pattern for boundary classes

**Output**: XML file for component diagram to import into OnlineCinema.vpp

### Task 2: Class Diagram for Control Layer
**Goal**: Create detailed class diagram for the Control Layer component

**Requirements**:
- Analyze commonality and variability of use cases from Stage 2
- Apply SOLID principles
- **Required design patterns**:
  - Facade (for boundary classes)
  - Template Method OR Strategy
- **Recommended patterns**:
  - Chain of Responsibility
- Include:
  - Main class with main() operation
  - Interfaces (realized and used)
  - Facade boundary classes
  - Abstract and concrete classes from patterns
  - Relationships: realization, generalization, association, dependency
  - Package containing all Control Layer classes
- The Control Layer should simulate Presentation Layer (I/O operations)
- Classes cannot depend on other components' classes (only on interfaces)

**Output**: XML file for Control Layer class diagram to import into OnlineCinema.vpp

## Proposed Workflow

### Phase 1: Project Analysis (CURRENT PHASE)
1. ✅ Read project structure
2. ✅ Read etap1, etap2, etap3 instructions
3. ✅ Create new git branch `etap3-implementation`
4. 🔄 Analyze OnlineCinema.vpp to understand existing diagrams
5. 🔄 Review teacher's example (IO-L Miniprojekt Biblioteka.pdf) for reference

### Phase 2: Architecture Design
1. Identify use cases from Stage 1
2. Review activity diagrams from Stage 2 (select 2 most complex)
3. Design 3-layer architecture (decide: MVC, MVP, or other BCE variant)
4. Define interfaces between layers
5. Create component diagram XML structure

### Phase 3: Control Layer Design
1. Analyze selected use cases for commonality/variability
2. Apply SOLID principles to identify classes
3. Design class hierarchy with required patterns:
   - Facade for boundary classes
   - Template Method or Strategy for use case execution
   - Chain of Responsibility (if applicable)
4. Define class attributes, operations, and relationships
5. Create class diagram XML structure

### Phase 4: XML Generation
1. Generate XML for component diagram (Task 1)
2. Generate XML for class diagram (Task 2)
3. Validate XML format compatible with Visual Paradigm
4. Save files in etap3/ directory

### Phase 5: Validation & Documentation
1. Test importing XML files into OnlineCinema.vpp
2. Verify diagrams are correctly structured
3. Create documentation/notes if needed
4. Commit changes to branch

## Files to Create
- `etap3/component_diagram.xml` - Component diagram for 3-layer architecture
- `etap3/control_layer_class_diagram.xml` - Class diagram for Control Layer
- `etap3/README.md` - Brief explanation of architecture decisions (optional)

## Questions for You to Confirm

1. **Architecture choice**: Should I use MVC, MVP, or another BCE architecture variant?
2. **Use cases**: Which use cases were defined in Stage 1? (I need to analyze the VPP file)
3. **Complexity**: Should I focus on the 2 most complex use cases from Stage 2, or consider all?
4. **XML format**: Do you want standalone XML files or specific Visual Paradigm XML format?

## Next Steps (After Your Approval)
Once you approve this workflow, I will:
1. Analyze the OnlineCinema.vpp file to extract existing use cases and activity diagrams
2. Review the teacher's example for XML format reference
3. Begin Phase 2: Architecture Design
4. Keep you updated at each major phase

---
**Status**: Awaiting your approval to proceed with Phase 1 (steps 4-5) and beyond.
