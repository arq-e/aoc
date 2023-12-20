package main.year2023;

import main.utils.AdventInputReader;
import main.utils.Day;
import main.utils.MathUtils;

import java.io.IOException;
import java.util.*;

public class Day20 extends Day {

    public static void main(String[] args) throws IOException {
        Day20 day = new Day20();

        List<String> input = AdventInputReader.getInput(day.getYear(), day.getDay());
        day.solve1(input);
        day.solve2(input);
    }

    public void solve1(List<String> list) {
        Map<String, Module> modules = parseModules(list);
        System.out.printf("Part 1 answer is: %d\n", iterate(modules,1000, null, null));
    }

    public void solve2(List<String> list) {
        Map<String, Module> modules = parseModules(list);
        String lastSender = findInputs(modules, "rx").get(0);
        List<String> prevStep = findInputs(modules, lastSender);
        List<Integer> cycles = new ArrayList<>();
        iterate(modules, 0, prevStep, cycles);
        long res = 1;
        for (int i : cycles) {
            res = MathUtils.lcm(i, res);
        }
        System.out.printf("Part 2 answer is: %d\n", res);
    }
        
    private long iterate(Map<String, Module> modules, int iterations, List<String> nodesToCheck, List<Integer> cycles) {

        int[] counts = new int[]{0,0};

        for (int i = 0; nodesToCheck == null ? i < iterations : nodesToCheck.size() > 0; ++i) {
            counts[0]++;
            TreeMap<Integer, String> order = new TreeMap<>();
            TreeMap<Integer, Integer> signals = new TreeMap<>();
            order.put(0, "broadcaster");
            signals.put(0, 0);
            int pos = 1;
            while (order.size() > 0) {
                String module = order.firstEntry().getValue();
                int cur = order.firstKey();
                int signal = signals.firstEntry().getValue();
                order.remove(cur);
                signals.remove(cur);
                if (modules.containsKey(module)) {
                    Module mod = modules.get(module);
                    if (mod.type.startsWith("%")) {
                        if (signal == 0) {
                            mod.state = (mod.state + 1) % 2;

                        } else continue;
                    } else if ( mod.type.startsWith("&")) {
                        mod.state = 0;
                        for (int inc : mod.incomingSignal.values()) {
                            if (inc == 0) {
                                mod.state = 1;
                                break;
                            }
                        }
                        if (nodesToCheck != null && mod.state == 1) {
                            String foundNode = "";
                            for (String node : nodesToCheck) {
                                if (node.equals(mod.name)) {
                                    cycles.add(i+1);
                                    foundNode = node;
                                    break; 
                                }
                            }
                            nodesToCheck.remove(foundNode);
                        }
                    }
                    for (String rec : mod.destinations) {
                        if (modules.containsKey(rec) && modules.get(rec).type.contains("&")) {
                            modules.get(rec).incomingSignal.put(module, mod.state);
                        }
                        if (mod.state == 0) ++counts[0];
                        else ++counts[1];
                        order.put(pos, rec);
                        signals.put(pos++, mod.state);
                    }

                }

            }
        }
        return counts[0] * counts[1];
    }

    private Map<String, Module> parseModules(List<String> list) {
        Map<String, Module> modules = new HashMap<>();
        for (String s : list) {
            String[] desc = s.replaceAll("[->,]", " ").split("\s+");
            Module module = new Module(desc);
            modules.put(module.name, module);
        }

        for (Module mod : modules.values()) {
            if (mod.type.contains("&")) {
                for (Module par : modules.values()) {
                    for (String s : par.destinations) {
                        if (mod.name.equals(s)) {
                            mod.incomingSignal.put(par.name, 0);
                        }
                    }
                }
            }
        }  
        return modules;      
    }

    private List<String> findInputs(Map<String, Module> modules, String cur) {
        List<String> inputs = new ArrayList<>();
        long res = 1;
        for (Module module : modules.values()) {
            for (String s : module.destinations) {
                if (cur.equals(s)) {
                    inputs.add(module.name);
                }
            }
        }    
        return inputs;    
    }

    public class Module{
        String name;
        String type;
        List<String> destinations;
        Map<String, Integer> incomingSignal;
        List<String> inputs;
        int state;

        public Module(String[] desc) {
            destinations = new ArrayList<>();
            inputs = new ArrayList<>();
            state = 0;
            if (desc[0].contains("broadcaster")) {
                for (int i = 1; i < desc.length; ++i) {
                    destinations.add(desc[i]);
                }
                name = desc[0];
                type = desc[0];
            } else if (desc[0].startsWith("%")) {
                for (int i = 1; i < desc.length; ++i) {
                    destinations.add(desc[i]);
                }
                name= desc[0].substring(1);
                type ="%";
            } else if (desc[0].startsWith("&")) {
                for (int i = 1; i < desc.length; ++i) {
                    destinations.add(desc[i]);
                }
                incomingSignal = new HashMap<>();
                name = desc[0].substring(1);
                type = "&";
            } else {
                name = desc[0];
                type = " ";
            }
        }
        
        @Override
        public boolean equals(Object obj) {
            if (obj.getClass() == Module.class) {
                Module second = (Module) obj;
                return this.name.equals(second.name);
            }
            return false;
        }

        @Override
        public int hashCode() {
            return this.name.hashCode();
        }
    }
}
