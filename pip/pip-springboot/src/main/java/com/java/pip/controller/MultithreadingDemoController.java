package com.java.pip.controller;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/multithreading")
@Slf4j
public class MultithreadingDemoController {

	private final Object lock = new Object();
	
	@Async("taskExecutor")
	public void asyncTask(String name) {
		
		log.debug("Async Task " + name + " started");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		log.debug("Async Task " + name + " finished");
	}
	
	@GetMapping("/async-demo")
	public String asyncDemo() {
		asyncTask("Task1");
		asyncTask("Task2");
		asyncTask("Task3");
		return "Async tasks started. Check console for details.";
		
	}
	
	@GetMapping("/executor-demo")
	public String executorDemo() {
		
		ExecutorService executor = Executors.newFixedThreadPool(3);
		
		for(int i=1; i<=5; i++) {
			int taskNum = i;
			executor.submit(() -> {
				log.debug("Executor task " + taskNum + " running in thread: " + Thread.currentThread().getName());
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			});
		}
		executor.shutdown();
		return "Executor tasks submitted. Check console for details.";
	}
	
	@GetMapping("/wait-notify-demo")
	public String waitNotifyDemo() throws InterruptedException {
		
		Thread t1 = new Thread(() -> {
			synchronized (lock) {
				try {
					log.debug("Thread 1 waiting...");
					lock.wait();
					log.debug("Thread 1 resumed!");
					Thread.sleep(2000);
					lock.notify();
				} catch (InterruptedException e) {
					log.debug("Thread 1 interrupted.");
				}
			}
		});
		
		Thread t2 = new Thread(() -> {
			synchronized (lock) {
				try {
					Thread.sleep(2000);
					log.debug("Thread 2 notifying...");
					lock.notify();
					log.debug("Thread 2 notified!");
					lock.wait();
				} catch (InterruptedException e) {
					log.debug("Thread 2 interrupted.");
				}
			}
		});
		
		t1.start();
		t2.start();
		
//		Thread.sleep(2000);
//		log.debug("Thread-1 interrupting.");
//		t1.interrupt();
//		log.debug("Thread-2 interrupting.");
//		t2.interrupt();
		
		t1.join();
		t2.join();
		
		return "Wait/Notify demo completed. Check console for details.";
		
	}
}