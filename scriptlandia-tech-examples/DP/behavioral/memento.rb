# memento.bsh

# Without violating encapsulation, capture and externalize an object's internal state
# so that the object can be restored to this state later.

# 1. This class don't want to reveal it's internal state and delegates presistance work to memento
#    object. This object should have access to private properties of enclosed class.

class Memento 
  def initialize(originator, memento_state)
    @originator = originator
    @memento_state = memento_state
  end

  def restore_memento
    @originator.state = @memento_state
  end
end

class Originator
  def initialize()
    @state = nil
  end

  def set_state(state)
    @state = state
  end

  def print_state
    puts state
  end

  def create_memento
    Memento(self, state).new
  end

  def restore_memento(m) {
    if (m.kind_of? Memento) {
      m.restore_memento
    end
  end
end

     

# 2. Caretaker holds previously creted mementos: storage 

class Caretaker
  def initialize
    saved_states = []
  end

  def add_memento(memento)
    @saved_states.add(memento)
  end
 
  def get_memento(index)
    saved_states.get(index)
  end
end

# 3. test

caretaker = Caretaker.new

originator = Originator.new

originator.setState("State1");
System.out.print("step1: "); originator.printState();

originator.setState("State2");
System.out.print("step2: "); originator.printState();

caretaker.addMemento( originator.createMemento() );
System.out.print("step3: "); originator.printState();

originator.setState("State3");
System.out.print("step4: "); originator.printState();

caretaker.addMemento( originator.createMemento() );
System.out.print("step5: "); originator.printState();

originator.setState("State4");
System.out.print("step6: "); originator.printState();

originator.restoreMemento( caretaker.getMemento(1) );
System.out.print("step7: "); originator.printState();
