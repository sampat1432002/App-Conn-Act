package com.example.app1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerChat;
    private EditText editMessage;
    private ImageButton buttonSend;

    private ChatAdapter chatAdapter;
    private List<ChatMessage> messageList;
    private Handler handler;
    private Random random;

    private final String[] autoReplies = {
            "That's interesting!",
            "Tell me more about that.",
            "I see what you mean.",
            "Thanks for sharing!",
            "Got it!",
            "That makes sense.",
            "Interesting point!",
            "I appreciate your message.",
            "Let me think about that...",
            "Great to hear from you!"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        setupRecyclerView();
        setupSendButton();
    }

    private void initViews() {
        recyclerChat = findViewById(R.id.recycler_chat);
        editMessage = findViewById(R.id.edit_message);
        buttonSend = findViewById(R.id.button_send);
        handler = new Handler(Looper.getMainLooper());
        random = new Random();
    }

    private void setupRecyclerView() {
        messageList = new ArrayList<>();
        chatAdapter = new ChatAdapter(messageList);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        recyclerChat.setLayoutManager(layoutManager);
        recyclerChat.setAdapter(chatAdapter);

        addWelcomeMessage();
    }

    private void addWelcomeMessage() {
        ChatMessage welcomeMessage = new ChatMessage(
                "Welcome to the chat! Send a message to get started.",
                false
        );
        messageList.add(welcomeMessage);
        chatAdapter.notifyItemInserted(messageList.size() - 1);
    }

    private void setupSendButton() {
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });
    }

    private void sendMessage() {
        String messageText = editMessage.getText().toString().trim();

        if (TextUtils.isEmpty(messageText)) {
            return;
        }

        ChatMessage sentMessage = new ChatMessage(messageText, true);
        messageList.add(sentMessage);
        chatAdapter.notifyItemInserted(messageList.size() - 1);
        recyclerChat.scrollToPosition(messageList.size() - 1);

        editMessage.setText("");

        simulateAutoReply();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }

    private void simulateAutoReply() {
        int delay = 1000 + random.nextInt(2000);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                String replyText = autoReplies[random.nextInt(autoReplies.length)];
                ChatMessage receivedMessage = new ChatMessage(replyText, false);
                messageList.add(receivedMessage);
                chatAdapter.notifyItemInserted(messageList.size() - 1);
                recyclerChat.scrollToPosition(messageList.size() - 1);
            }
        }, delay);
    }
}
