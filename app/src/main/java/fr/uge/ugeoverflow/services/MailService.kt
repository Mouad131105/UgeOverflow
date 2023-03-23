package fr.uge.ugeoverflow.services

import fr.uge.ugeoverflow.api.OneQuestionResponse
import fr.uge.ugeoverflow.session.SessionManager
import fr.uge.ugeoverflow.session.SessionManagerSingleton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*
import javax.mail.*
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

object MailService  {

    suspend fun sendEmailToNotifyAdmin(question : OneQuestionResponse) = withContext(Dispatchers.IO) {
        val sessionManager = SessionManagerSingleton.sessionManager
        val currentUser = sessionManager.getLoggedInUsername()
        //properties corresponding to microsoft office smtp mailing service
        val properties = Properties()
        properties["mail.smtp.auth"] = "true"
        properties["mail.smtp.starttls.enable"] = "true"
        properties["mail.smtp.host"] = "smtp.office365.com"
        properties["mail.smtp.port"] = "587"


        val senderMail = "ugeoverflow-team@outlook.com"
        val senderPassword = "Annexekenitra40"


        val fromAddress = InternetAddress(senderMail)
        val receiverMail = InternetAddress("issamgrn1@gmail.com")
        val subject = "Signaled question on UGEOverflow app"
        val body = "Dear Admin, \n\n"+
                "We want to inform you that the user $currentUser has signaled a question on the UGEOverflow application. The details of the question are as follows:\n" +
                "Title: "+ question.title + "\n" +
                "Tags: " + question.tags + "\n\n" +
                "This question has been flagged by $currentUser as inappropriate or offensive. Please take necessary actions to review the question and take appropriate actions as required.\n" +
                "We request you to take immediate action and review the question as soon as possible.\n\n" +
                "Thank you for your attention to this matter.\n" +
                "\n" +
                "Best regards,\n" +
                "The UGEOverflow Team."

        // on crée une session pour que ensuite on envoie le message
        val session = Session.getInstance(properties, object : Authenticator() {
            override fun getPasswordAuthentication(): PasswordAuthentication {
                return PasswordAuthentication(senderMail, senderPassword)
            }
        })
        val message = MimeMessage(session)
        message.setFrom(fromAddress)
        message.setRecipient(Message.RecipientType.TO, receiverMail)
        message.subject = subject
        message.setText(body)


        Transport.send(message)
    }


}