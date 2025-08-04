# Aerius: AI-Powered Real-Time Multilingual Communication Platform
Aerius is a sophisticated real-time communication platform engineered to dissolve language barriers in live audio and video conversations. The application enables multiple users to engage in a seamless dialogue, each speaking and listening in their own preferred language. By integrating a suite of advanced AI services, Aerius provides instantaneous translation, creating an inclusive and effective communication environment for global teams, international business, and cross-cultural interactions.

The core of the system captures a speaker's audio, transcribes it to text, translates that text into the chosen languages of the other participants, and synthesizes the translated text back into natural-sounding speech, all with minimal latency. This ensures that conversations flow naturally, as if all participants were speaking the same language. The platform is built on a robust Java-based backend, utilizing WebRTC for high-quality, secure, peer-to-peer communication and a suite of cloud AI APIs for transcription, translation, and speech synthesis.

## Endpoints
| Module                | Method      | Endpoint                                         | Description                                                        |
|-----------------------|------------|--------------------------------------------------|--------------------------------------------------------------------|
| **Authentication**    | POST       | /api/auth/register                               | Registers a new user.                                              |
|                       | POST       | /api/auth/login                                  | Authenticates a user and returns JWT access/refresh tokens.        |
|                       | POST       | /api/auth/logout                                 | Logs the user out and invalidates the session.                     |
|                       | POST       | /api/auth/refresh-token                          | Issues a new access token using a refresh token.                   |
| **User Management**   | GET        | /api/users/me                                    | Retrieves the profile of the currently authenticated user.         |
|                       | PUT        | /api/users/me                                    | Updates the profile of the currently authenticated user.           |
|                       | PUT        | /api/users/me/password                           | Allows the authenticated user to change their password.            |
|                       | DELETE     | /api/users/me                                    | Deletes the account of the authenticated user.                     |
|                       | GET        | /api/users/me/preferences                        | Gets user-specific preferences (e.g., default language).           |
|                       | PUT        | /api/users/me/preferences                        | Updates user-specific preferences.                                 |
| **Conversation**      | POST       | /api/conversations                               | Creates a new conversation room.                                   |
|                       | GET        | /api/conversations                               | Retrieves a list of all conversations the user is a member of.     |
|                       | GET        | /api/conversations/history                       | Retrieves the authenticated user's past conversation history.       |
|                       | GET        | /api/conversations/{conversationId}              | Retrieves details for a specific conversation.                     |
|                       | PUT        | /api/conversations/{conversationId}              | Updates conversation details (e.g., topic). Owner only.            |
|                       | DELETE     | /api/conversations/{conversationId}              | Deletes a conversation. Owner only.                                |
|                       | POST       | /api/conversations/{conversationId}/join         | Allows a user to join an existing conversation.                    |
|                       | POST       | /api/conversations/{conversationId}/leave        | Allows a user to leave a conversation.                             |
|                       | POST       | /api/conversations/{conversationId}/invite       | Invites another user to the conversation.                          |
|                       | GET        | /api/conversations/{conversationId}/participants | Gets a list of all participants in a conversation.                 |
| **Translation & Data**| GET        | /api/conversations/{conversationId}/transcript   | Retrieves the full text transcript of a completed conversation.    |
|                       | GET        | /api/config/languages                            | Fetches a list of all supported languages for translation.         |
| **Signaling**         | WebSocket  | /ws/signal/{conversationId}                      | WebSocket for real-time WebRTC signaling and live events.          |
| **Admin**             | GET        | /api/admin/users                                 | Retrieves a list of all users in the system.                       |
|                       | GET        | /api/admin/users/{userId}                        | Retrieves the profile of a specific user.                          |
|                       | PUT        | /api/admin/users/{userId}                        | Updates a specific user's profile.                                 |
|                       | DELETE     | /api/admin/users/{userId}                        | Deletes or deactivates a specific user's account.                  |
|                       | GET        | /api/admin/conversations                         | Retrieves a list of all conversations on the platform.             |
|                       | GET        | /api/admin/analytics/summary                     | Retrieves platform usage statistics.                               |
