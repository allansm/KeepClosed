package nameless;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Test {
	public static void main(String[] args) {
		while(true)
		try {
			System.out.println("initializing..");
			System.out.println("reading config..");
			List<String> proc = Files.readAllLines(Paths.get("config"));
			List<String> currentProc = new ArrayList<String>();
			Scanner s = new Scanner(Runtime.getRuntime().exec("tasklist").getInputStream());
			System.out.println("getting current process..");
			while(s.hasNext()) {
				String next = s.next();
				try {
					if(next.charAt(next.length()-4) == '.' && next.charAt(next.length()-3) == 'e' && next.charAt(next.length()-2) == 'x') {
						currentProc.add(next);
					}
					Thread.sleep(1);
				}catch(Exception e) {
					
				}
			}
			boolean run = false;
			System.out.println("starting..");
			for(String cp:currentProc) {
				for(String p:proc) {
					if(cp.equalsIgnoreCase(p)) {
						run = true;
						break;
					}
				}
				if(run) {
					break;
				}
			}
			if(run) {
				System.out.println("finded.");
				for(String p:proc) {
					new Thread(new Runnable() {
						@Override
						public void run() {
							int count = 0;
							while(count < 60) {
								System.out.println(p+" "+count);
								try {
									Runtime.getRuntime().exec("taskkill /f /im "+p);
									Thread.sleep(1000);
									count++;
								}catch(Exception e) {
									
								}
							}
							count = 0;
						}
					}).start();
				}
				try {
					System.out.println("waiting threads end...");
					Thread.sleep(61000);
					System.out.println("restarting..");
				}catch(Exception e) {}
			}else {
				try {
					System.out.println("no process finded.");
					System.out.println("waiting to retry..");
					Thread.sleep(61000);
				}catch(Exception e) {}
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
}
