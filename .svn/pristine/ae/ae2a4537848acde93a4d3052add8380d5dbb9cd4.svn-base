package game.models;

import java.util.ArrayList;

public class CooldownSheduler implements Runnable{

	public static final long MAIN_TICK_SHEDULE = 50;
	
	private volatile boolean running; 
	
	private ArrayList<Runnable> tasks;
	private Thread shedulerThread;
	private long tickInterval;
	
	private static CooldownSheduler mainInstance;
	
	
	public CooldownSheduler(long interval) {
		tasks = new ArrayList<Runnable>();
		this.tickInterval = interval;
	}

	public void addTask(Runnable task){
		tasks.add(task);
	}
	
	public void start(){
		if(!running || shedulerThread == null){
			
			running = true;
			
			shedulerThread = new Thread(this);
			shedulerThread.setName("Sheduler-"+shedulerThread.getId());
			shedulerThread.start();
		}
	}
	
	@Override
	public void run() {
		try {
			while(running){
				long updateStart = System.nanoTime();
				
				//do tasks
				for (Runnable task : tasks) {
					task.run();
				}
				
				long updateTime = System.nanoTime() - updateStart;
			
				Thread.sleep(tickInterval-(updateTime/1000000));
			}

			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}

	public void stop() {
		running = false;
	}
	
	public static CooldownSheduler getMainSheduler(){
		if(mainInstance == null)	{
			synchronized (CooldownSheduler.class) {
				
				//double lock
				if(mainInstance == null){
					mainInstance = new CooldownSheduler(MAIN_TICK_SHEDULE);
					mainInstance.start();
				}
			}	
		}
		return mainInstance;
	}
	
}
