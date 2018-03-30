package cf.techfusion.yasin.buddybot.support;


import android.os.AsyncTask;

import ai.api.AIDataService;
import ai.api.AIServiceException;
import ai.api.android.AIConfiguration;
import ai.api.model.AIRequest;
import ai.api.model.AIResponse;

public class AiSupport {
    public static final String API_KEY = "--CLIENT-ID--";
    public static final AIConfiguration CONFIGURATION = new AIConfiguration(API_KEY,
            AIConfiguration.SupportedLanguages.English,
            AIConfiguration.RecognitionEngine.System);
    public final static AIDataService aiDataService = new AIDataService(CONFIGURATION);


    public static AIRequest createRequest(String query){
        AIRequest ai = new AIRequest();
        ai.setQuery(query);
        return ai;
    }


    public static AIResponse processQuery(AIRequest request) {
        try{
            return aiDataService.request(request);
        }catch (AIServiceException e) {
        }
        return null;
    }

    public static abstract class AiRequestTask extends AsyncTask<AIRequest, Void, AIResponse>{
        @Override
        protected AIResponse doInBackground(AIRequest... aiRequests) {
            return processQuery(aiRequests[0]);
        }
    }

}
