---
name: Bug Report
about: Submitting a bug report / バグ報告をする場合
title: "[Bug] ..."
labels: bug
assignees: SlashNephy

---

English and Japanese are also available in bug reporting.  
バグ報告は日本語と英語のどちらで記載しても構いません。  

**Description / 概要:**  
A clear and concise description of what the bug is.  
どのようなバグであるのかを明確かつ簡潔に説明してください。  

e.g.) Status.fullText() doesn't return full-length status text. Text body seems to be omitted as "...".

**Example Codes To Reproduce / バグ再現コード:**  
Shows example source codes to be able to reproduce the behavior.  
このバグを再現できるようにソースコードを例示してください。  

```kotlin=
fun main() {
    val client = PenicillinClient {
        application("XXX", "YYY")
        token("xxx", "yyy")
        // You should conceal your credentials.
        // 資格情報を掲載しないようご注意ください。
    }

    client.timeline.home().complete().forEach {
        println(it.fullText())
    }
}
```

**Expected Behavior / 期待された結果:**  
A clear and concise description of what you expected to happen.  
実際には期待された動作について, 明確かつ簡潔に知らせてください。  

e.g.) Status.fullText() will return full-length status text without omitting as "...".

**Emvironment / 環境:**  
Please complete the following information to specify the problem.  
問題を特定するために以下の情報を埋めてください。  

 - OS: [e.g. Windows 10 Pro 64 bit]
 - Java Version: [e.g. Java 8 Update 191]
 - Penicillin Version: [e.g. 4.0.0]
 - Kotlin Version: [e.g. 1.3.11]
 - Twitter Application: [e.g. A third-party app with read & write permissions]

**Additional context / 備考:**  
Add any other context about the problem here.  
問題の報告に際して, 他に記すことがあれば記載してください。  
