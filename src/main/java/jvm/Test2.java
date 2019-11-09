package jvm;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import com.sun.tools.attach.AgentInitializationException;
import com.sun.tools.attach.AgentLoadException;
import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor;

public class Test2 {

	public static void main(String[] args) throws Exception {
		List<VirtualMachineDescriptor> list = VirtualMachine.list();
		String pid = "";

		for (VirtualMachineDescriptor vmd : list) {
			System.out.println("pid:" + vmd.id() + ":" + vmd.displayName());
			pid = vmd.id();
		}

		
		// Attach到JVM上
		VirtualMachine virtualmachine = VirtualMachine.attach(pid);
		// 加载Agent
		String javaHome = virtualmachine.getSystemProperties().getProperty("java.home");
		String agentPath = javaHome + File.separator + "jre" + File.separator + "lib" + File.separator
				+ "management-agent.jar";
		File file = new File(agentPath);
		if (!file.exists()) {
			agentPath = javaHome + File.separator + "lib" + File.separator + "management-agent.jar";
			file = new File(agentPath);
			if (!file.exists())
				throw new IOException("Management agent not found");
		}

		agentPath = file.getCanonicalPath();
		try {
			virtualmachine.loadAgent(agentPath, "com.sun.management.jmxremote");
		} catch (AgentLoadException e) {
			throw new IOException(e);
		} catch (AgentInitializationException agentinitializationexception) {
			throw new IOException(agentinitializationexception);
		}
		Properties properties = virtualmachine.getAgentProperties();
		String address = (String) properties.get("com.sun.management.jmxremote.localConnectorAddress");
		System.out.println("address:"+address);
		virtualmachine.detach();
	}

}
//tools.jar needs to be added to the IDE's library path and the program's classpath. The tools.jar file is found in the JDK's lib directory.