

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CallableFutureExample implements Callable<String> {
	String currentCallbackDataReqPojo = null;

	CallableFutureExample(String getCallbackDataResPojo) {
		this.currentCallbackDataReqPojo = getCallbackDataResPojo;
	}

	static List<String> callbackDataResPojos = new ArrayList<>();


	public static void main(String[] args) {
		System.out.println("Starting Callback");

		System.out.println("Getting callback data from db...");
		callbackDataResPojos = getAllCallBackResponseFromDb();
		System.out.println("Data found : " + callbackDataResPojos.size());
		if (callbackDataResPojos.size() > 0) {
			ExecutorService executor = Executors.newFixedThreadPool(10);
			// List<Future<StatusUpdateCallbackResDto>> list = new
			// ArrayList<Future<StatusUpdateCallbackResDto>>();
			for (int i = 0; i < callbackDataResPojos.size(); i++) {
				Callable<String> callable = new CallableFutureExample(callbackDataResPojos.get(i));
//				Future<StatusUpdateCallbackResDto> future = executor.submit(callable);
//				list.add(future);
				executor.submit(callable);
			}
//			for (Future<StatusUpdateCallbackResDto> fut : list) {
//				try {
//					System.out.println("refId: {}, response: {}" + fut.get().getRefId(), fut.get());
//				} catch (InterruptedException | ExecutionException e) {
//					e.printStackTrace();
//				}
//			}
			executor.shutdown();
		}
	}

	public static List<String> getAllCallBackResponseFromDb() {
		List<String> res = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			String callbackDataResPojo = new String();
			Random random = new Random();
			res.add(callbackDataResPojo+random);
		}
		return res;
	}

	public static String callStatusUpdateCallbackApi(String callbackDataResPojo)
			throws InterruptedException {
		String callbackResPojo = null;
		Random random = new Random();
		try {
			String callbackReqPojo = new String();

			System.out.println("refId:{}, callback Api Request :{}"+ callbackReqPojo);

			callbackResPojo = random.nextInt(100) > 50 ? "Success" : "Failed";
			Thread.sleep(random.nextInt(10000));
			System.out.println("refId:{}, callback Api Response:{}"+ callbackResPojo);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return callbackResPojo;
	}

	@Override
	public String call() throws Exception {
		System.out.println("refid: {}, threadName: {}"+ this.currentCallbackDataReqPojo+
				Thread.currentThread().getName());
		String callbackApiResponse = callStatusUpdateCallbackApi(this.currentCallbackDataReqPojo);

		if (!"Success".equalsIgnoreCase(callbackApiResponse)) {
			System.out.println("Calling again as we received error Response: {}"+callbackApiResponse);
			callbackApiResponse = this.call();
		}

		return callbackApiResponse;
	}

}
