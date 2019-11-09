package jvm;

import java.util.List;

import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor;

/**
 * windows 执行 权限问题可能会导致只输出当前进程得id
 * @author Zengmin.Zhang
 *
 */
public class Test {

	public static void main(String[] args) throws Exception {
		List<VirtualMachineDescriptor> list = VirtualMachine.list();
		for (VirtualMachineDescriptor vmd : list) {
			System.out.println("pid:" + vmd.id() + ":" + vmd.displayName());
		}

	}

}
//tools.jar needs to be added to the IDE's library path and the program's classpath. The tools.jar file is found in the JDK's lib directory.