package com.bytedesk.visitorandroid

import android.annotation.SuppressLint
import android.webkit.JavascriptInterface
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import coil.compose.AsyncImage
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.bytedesk.visitorandroid.ui.theme.VisitorAndroidTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private data class VisitorTabItem(val label: String, val icon: androidx.compose.ui.graphics.vector.ImageVector)
private data class DemoUser(val key: String, val visitorUid: String, val nickname: String, val avatar: String, val vipLevel: Int)
private data class BizScene(val value: String, val label: String, val description: String, val autoSendBizInfo: Boolean = true)
private data class ChatProfile(val org: String, val type: String, val sid: String)
private data class ChatDestination(val title: String, val url: String)
private data class DetailDestination(val kind: String, val payload: JSONObject)
private data class BubbleClickEvent(val uid: String, val type: String, val content: Any?, val extra: Any?)
private data class ThreadRouteTarget(val type: String, val sid: String)
private data class PreviewLabel(val match: String, val fallback: String)
private data class ThreadSummary(
    val raw: JSONObject,
    val uid: String,
    val type: String,
    val title: String,
    val preview: String,
    val updatedAt: String,
    val unreadCount: Int,
    val avatar: String,
    val isPlatformThread: Boolean,
) {
    val typeLabel: String
        get() = when (type) {
            "0" -> "一对一"
            "1" -> "工作组"
            "2" -> "机器人"
            else -> "历史"
        }

    val typeColor: Color
        get() = when (type) {
            "0" -> Color(0xFF1F6F54)
            "1" -> Color(0xFF2C5D9F)
            "2" -> Color(0xFF9C6D14)
            else -> Color(0xFF6D746C)
        }

    companion object {
        fun fromJson(thread: JSONObject): ThreadSummary {
            return ThreadSummary(
                raw = thread,
                uid = thread.optString("uid"),
                type = normalizeThreadType(thread),
                title = resolveThreadName(thread),
                preview = resolveThreadPreview(thread),
                updatedAt = formatTime(thread.optString("updatedAt", thread.optString("createdAt"))),
                unreadCount = thread.optInt("visitorUnreadCount", thread.optInt("unreadCount", 0)),
                avatar = resolveThreadAvatar(thread),
                isPlatformThread = thread.optString("orgUid") == DEFAULT_CHAT_PROFILE.org,
            )
        }
    }
}

private const val CHAT_BASE_URL = "https://cdn.weiyuai.cn"
private const val API_BASE_URL = "https://api.weiyuai.cn"
private const val MESSAGE_BUBBLE_CLICK_EVENT_NAME = "MESSAGE_BUBBLE_CLICK"
private const val GOODS_MESSAGE_TYPE = "GOODS"
private const val ORDER_MESSAGE_TYPE = "ORDER"
private val DEFAULT_CHAT_PROFILE = ChatProfile(org = "df_org_uid", type = "1", sid = "df_wg_uid")

private val visitorTabs = listOf(
    VisitorTabItem("首页", Icons.Filled.Home),
    VisitorTabItem("消息", Icons.Filled.Email),
    VisitorTabItem("我的", Icons.Filled.Person),
)

private val demoUsers = listOf(
    DemoUser("user1", "visitor_001", "用户小明", "https://weiyuai.cn/assets/images/avatar/02.jpg", 0),
    DemoUser("user2", "visitor_002", "用户小红", "https://weiyuai.cn/assets/images/avatar/01.jpg", 1),
    DemoUser("user3", "visitor_003", "用户小美", "https://weiyuai.cn/assets/images/avatar/03.jpg", 2),
)

private val bizScenes = listOf(
    BizScene("plain", "普通会话演示", "直接打开普通客服会话，不携带商品卡片和订单卡片参数。", true),
    BizScene("goods", "商品消息演示", "打开商品场景客服会话，并自动发送商品卡片。", true),
    BizScene("goods-confirm", "商品消息演示（弹窗确认发送）", "打开商品场景客服会话，通过弹窗确认后再发送商品卡片。", false),
    BizScene("order", "订单消息演示", "打开订单场景客服会话，并自动发送订单卡片。", true),
    BizScene("order-confirm", "订单消息演示（弹窗确认发送）", "打开订单场景客服会话，通过弹窗确认后再发送订单卡片。", false),
)

private val goodsInfoDemo = JSONObject().apply {
    put("uid", "goods_uniapp_001")
    put("title", "轻奢通勤双肩包")
    put("image", "https://images.unsplash.com/photo-1548036328-c9fa89d128fa?auto=format&fit=crop&w=900&q=80")
    put("description", "适合日常通勤和短途出差的多功能双肩包。")
    put("price", 399)
    put("url", "https://www.weiyuai.cn")
    put("tagList", JSONArray(listOf("新品", "包邮", "支持7天无理由")))
    put("quantity", 1)
    put("shopUid", "shop_001")
}

private val orderInfoDemoBase = JSONObject().apply {
    put("uid", "order_uniapp_001")
    put("shopUid", "shop_001")
    put("time", "2026-03-11 10:30:00")
    put("status", "paid")
    put("statusText", "已付款，待发货")
    put("goods", JSONObject(goodsInfoDemo.toString()))
    put("totalAmount", 399)
    put("shippingAddress", JSONObject().apply {
        put("name", "张三")
        put("phone", "13800000000")
        put("address", "上海市浦东新区世纪大道 100 号")
    })
    put("paymentMethod", "支付宝")
}

private val threadPreviewLabels = listOf(
    PreviewLabel("IMAGE", "[图片]"),
    PreviewLabel("VOICE", "[语音]"),
    PreviewLabel("AUDIO", "[语音]"),
    PreviewLabel("VIDEO", "[视频]"),
    PreviewLabel("FILE", "[文件]"),
    PreviewLabel("LOCATION", "[位置]"),
    PreviewLabel("PHONE", "[电话]"),
    PreviewLabel("EMAIL", "[邮箱]"),
    PreviewLabel("WECHAT", "[微信]"),
    PreviewLabel("GOODS", "[商品]"),
    PreviewLabel("ORDER", "[订单]"),
    PreviewLabel("ARTICLE", "[文章]"),
    PreviewLabel("FORM", "[表单]"),
    PreviewLabel("THREAD", "[会话]"),
    PreviewLabel("TICKET", "[工单]"),
)

private val threadTranslations = mapOf(
    "i18n.agent.nickname" to "默认客服",
    "i18n.welcome.tip" to "您好，有什么可以帮您的?",
    "i18n.default.welcome.message" to "您好，请问有什么可以帮助您？",
    "i18n.robot.nickname" to "默认机器人",
    "i18n.robot.agent.assistant.nickname" to "默认智能助手",
    "i18n.unified.nickname" to "统一客服入口",
    "i18n.workgroup.nickname" to "默认工作组",
    "i18n.workgroup.booking.nickname" to "Booking 工作组",
    "i18n.workgroup.before.nickname" to "售前工作组",
    "i18n.workgroup.after.nickname" to "售后工作组",
    "i18n.workgroup.ticket.nickname" to "工单工作组",
)

private const val BUBBLE_CLICK_BRIDGE_SCRIPT = """
(function() {
    if (window.__bytedeskBubbleBridgeInstalled) {
        return;
    }
    window.__bytedeskBubbleBridgeInstalled = true;

    function sendToNative(payload) {
        try {
            var text = typeof payload === 'string' ? payload : JSON.stringify(payload);
            if (window.BytedeskBubbleBridge && window.BytedeskBubbleBridge.postMessage) {
                window.BytedeskBubbleBridge.postMessage(text);
            }
        } catch (error) {}
    }

    function forward(packet) {
        if (packet === undefined || packet === null) {
            return;
        }
        sendToNative(packet);
    }

    function wrapUniBridge(bridge) {
        var uniBridge = bridge || {};
        var originalUniPostMessage = uniBridge.postMessage;
        uniBridge.postMessage = function(packet) {
            var payload = packet && packet.data !== undefined ? packet.data : packet;
            forward(payload);
            if (typeof originalUniPostMessage === 'function') {
                return originalUniPostMessage.apply(this, arguments);
            }
        };
        return uniBridge;
    }

    function wrapWxBridge(bridge) {
        var wxBridge = bridge || {};
        var miniProgramBridge = wxBridge.miniProgram || {};
        var originalMiniProgramPostMessage = miniProgramBridge.postMessage;
        miniProgramBridge.postMessage = function(packet) {
            var payload = packet && packet.data !== undefined ? packet.data : packet;
            forward(payload);
            if (typeof originalMiniProgramPostMessage === 'function') {
                return originalMiniProgramPostMessage.apply(this, arguments);
            }
        };
        wxBridge.miniProgram = miniProgramBridge;
        return wxBridge;
    }

    function installWindowBridgeProxy(propertyName, wrapper) {
        var currentValue = wrapper(window[propertyName]);
        try {
            Object.defineProperty(window, propertyName, {
                configurable: true,
                get: function() {
                    return currentValue;
                },
                set: function(nextValue) {
                    currentValue = wrapper(nextValue);
                }
            });
        } catch (error) {
            currentValue = wrapper(window[propertyName]);
        }
        window[propertyName] = currentValue;
    }

    function installHostBridges() {
        installWindowBridgeProxy('uni', wrapUniBridge);
        installWindowBridgeProxy('wx', wrapWxBridge);
    }

    window.addEventListener('message', function(event) {
        forward(event && event.data !== undefined ? event.data : event);
    }, false);

    var originalPostMessage = window.postMessage;
    window.postMessage = function(message, targetOrigin, transfer) {
        forward(message);
        if (typeof originalPostMessage === 'function') {
            return originalPostMessage.apply(this, arguments);
        }
    };

    installHostBridges();
})();
"""

private const val ANDROID_VIEWPORT_FIX_SCRIPT = """
(function() {
    if (window.__bytedeskAndroidViewportFixInstalled) {
        if (typeof window.__bytedeskSyncViewportHeight === 'function') {
            window.__bytedeskSyncViewportHeight();
        }
        return;
    }
    window.__bytedeskAndroidViewportFixInstalled = true;

    function syncViewportHeight() {
        var viewportHeight = window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight;
        if (!viewportHeight) {
            return;
        }

        document.documentElement.style.height = viewportHeight + 'px';
        document.documentElement.style.minHeight = viewportHeight + 'px';

        if (document.body) {
            document.body.style.height = viewportHeight + 'px';
            document.body.style.minHeight = viewportHeight + 'px';
        }

        var root = document.getElementById('root');
        if (root) {
            root.style.height = viewportHeight + 'px';
            root.style.minHeight = viewportHeight + 'px';
        }

        var chatPage = document.querySelector('.chat-page');
        if (chatPage) {
            chatPage.style.height = viewportHeight + 'px';
            chatPage.style.minHeight = viewportHeight + 'px';
        }

        var chatApp = document.querySelector('.ChatApp');
        if (chatApp) {
            chatApp.style.height = viewportHeight + 'px';
            chatApp.style.minHeight = viewportHeight + 'px';
        }
    }

    window.__bytedeskSyncViewportHeight = syncViewportHeight;
    syncViewportHeight();
    window.addEventListener('resize', syncViewportHeight, false);
    window.addEventListener('orientationchange', syncViewportHeight, false);
    document.addEventListener('visibilitychange', function() {
        if (!document.hidden) {
            syncViewportHeight();
        }
    }, false);
    setTimeout(syncViewportHeight, 0);
    setTimeout(syncViewportHeight, 120);
    setTimeout(syncViewportHeight, 360);
})();
"""

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VisitorAndroidTheme {
                VisitorAndroidApp()
            }
        }
    }
}

@Composable
fun VisitorAndroidApp() {
    var selectedTab by rememberSaveable { mutableIntStateOf(0) }
    var selectedUserIndex by rememberSaveable { mutableIntStateOf(0) }
    var searchText by rememberSaveable { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var errorText by remember { mutableStateOf<String?>(null) }
    val threads = remember { mutableStateListOf<ThreadSummary>() }
    var openedChat by remember { mutableStateOf<ChatDestination?>(null) }
    var openedDetail by remember { mutableStateOf<DetailDestination?>(null) }
    val scope = rememberCoroutineScope()
    val selectedUser = demoUsers[selectedUserIndex]

    LaunchedEffect(selectedUserIndex, searchText) {
        isLoading = true
        errorText = null
        try {
            threads.clear()
            threads.addAll(fetchVisitorThreads(selectedUser, searchText.trim()))
        } catch (error: Exception) {
            errorText = error.message ?: "加载历史会话失败"
        } finally {
            isLoading = false
        }
    }

    if (openedChat != null) {
        Box(modifier = Modifier.fillMaxSize()) {
            ChatScreen(
                destination = openedChat!!,
                onBack = {
                    openedDetail = null
                    openedChat = null
                },
            ) { detail ->
                openedDetail = detail
            }

            if (openedDetail != null) {
                when (openedDetail!!.kind) {
                    GOODS_MESSAGE_TYPE -> GoodsDetailScreen(openedDetail!!.payload, onBack = { openedDetail = null })
                    ORDER_MESSAGE_TYPE -> OrderDetailScreen(openedDetail!!.payload, onBack = { openedDetail = null })
                }
            }
        }
        return
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color(0xFFF4F6F2),
        bottomBar = {
            NavigationBar {
                visitorTabs.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        icon = { Icon(item.icon, contentDescription = item.label) },
                        label = { Text(item.label) },
                    )
                }
            }
        },
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            Text(
                text = when (selectedTab) {
                    0 -> "微语"
                    1 -> "历史会话"
                    else -> "我的"
                },
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp),
            )
            when (selectedTab) {
                0 -> HomeTab(
                    onSceneClick = { scene ->
                        openedChat = ChatDestination(title = sceneTitle(scene), url = buildSceneChatUrl(scene, selectedUser))
                    },
                )
                1 -> MessagesTab(
                    searchText = searchText,
                    onSearchChange = { searchText = it },
                    threads = threads,
                    loading = isLoading,
                    errorText = errorText,
                    onRetry = {
                        scope.launch {
                            isLoading = true
                            errorText = null
                            try {
                                threads.clear()
                                threads.addAll(fetchVisitorThreads(selectedUser, searchText.trim()))
                            } catch (error: Exception) {
                                errorText = error.message ?: "加载历史会话失败"
                            } finally {
                                isLoading = false
                            }
                        }
                    },
                    onThreadClick = { thread ->
                        val target = resolveThreadRouteTarget(thread.raw)
                        if (target.sid.isNotEmpty()) {
                            openedChat = ChatDestination(
                                title = thread.title,
                                url = buildChatUrl(
                                    chatProfile = ChatProfile(DEFAULT_CHAT_PROFILE.org, target.type, target.sid),
                                    visitorProfile = selectedUser,
                                ),
                            )
                        } else {
                            errorText = "当前会话缺少 sid"
                        }
                    },
                )
                else -> ProfileTab(
                    users = demoUsers,
                    selectedUserIndex = selectedUserIndex,
                    onUserSelect = { selectedUserIndex = it },
                )
            }
        }
    }
}

@Composable
private fun HomeTab(
    onSceneClick: (BizScene) -> Unit,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 16.dp, vertical = 4.dp),
    ) {
        item {
            DemoCard {
                Column {
                    bizScenes.forEachIndexed { index, scene ->
                        Row(
                            modifier = Modifier.fillMaxWidth().clickable { onSceneClick(scene) }.padding(vertical = 16.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                        ) {
                            AvatarBubble(letter = scene.label.take(1), tint = Color(0xFF2C5D9F))
                            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                                Text(scene.label, style = MaterialTheme.typography.titleSmall)
                                Text(scene.description, style = MaterialTheme.typography.bodyMedium, color = Color(0xFF56655B))
                            }
                            Text("›", style = MaterialTheme.typography.headlineMedium, color = Color(0xFF7E8A82))
                        }
                        if (index < bizScenes.lastIndex) {
                            HorizontalDivider(color = Color(0xFFE8EEEA))
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MessagesTab(
    searchText: String,
    onSearchChange: (String) -> Unit,
    threads: List<ThreadSummary>,
    loading: Boolean,
    errorText: String?,
    onRetry: () -> Unit,
    onThreadClick: (ThreadSummary) -> Unit,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 16.dp, vertical = 4.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        item {
            DemoCard {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 14.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Icon(Icons.Filled.Search, contentDescription = null, tint = Color(0xFF6D746C))
                    TextField(
                        value = searchText,
                        onValueChange = onSearchChange,
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("搜索会话标题或消息内容") },
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = Color.Transparent,
                            focusedContainerColor = Color.Transparent,
                            disabledContainerColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                        ),
                    )
                }
            }
        }
        if (loading) {
            item {
                DemoCard {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        CircularProgressIndicator(modifier = Modifier.size(20.dp), strokeWidth = 2.dp)
                        Text("正在加载历史会话...")
                    }
                }
            }
        } else if (errorText != null && threads.isEmpty()) {
            item {
                DemoCard {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Text(errorText)
                        FilterChip(selected = false, onClick = onRetry, label = { Text("重新加载") })
                    }
                }
            }
        } else if (threads.isEmpty()) {
            item {
                DemoCard {
                    Text("当前访客暂无历史会话")
                }
            }
        } else {
            item {
                DemoCard {
                    Column {
                        threads.forEachIndexed { index, thread ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { onThreadClick(thread) }
                                    .padding(vertical = 16.dp),
                                horizontalArrangement = Arrangement.spacedBy(12.dp),
                            ) {
                                AvatarBubble(letter = thread.title.take(1), tint = thread.typeColor)
                                Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                                    Row {
                                        Text(thread.title, style = MaterialTheme.typography.titleSmall, modifier = Modifier.weight(1f))
                                        Text(thread.updatedAt, style = MaterialTheme.typography.bodySmall, color = Color(0xFF79867E))
                                    }
                                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                        if (thread.isPlatformThread) {
                                            Surface(
                                                color = Color(0x1F2C5D9F),
                                                shape = RoundedCornerShape(999.dp),
                                            ) {
                                                Text(
                                                    text = "平台",
                                                    color = Color(0xFF2C5D9F),
                                                    style = MaterialTheme.typography.labelSmall,
                                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                                )
                                            }
                                        }
                                        Text(
                                            thread.preview,
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = Color(0xFF56655B),
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis,
                                            modifier = Modifier.weight(1f),
                                        )
                                    }
                                    Row {
                                        Spacer(modifier = Modifier.weight(1f))
                                        if (thread.unreadCount > 0) {
                                            BadgedBox(badge = { Badge { Text(thread.unreadCount.toString()) } }) {
                                                Spacer(modifier = Modifier.size(1.dp))
                                            }
                                        }
                                    }
                                }
                            }
                            if (index < threads.lastIndex) {
                                HorizontalDivider(color = Color(0xFFE8EEEA))
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ProfileTab(
    users: List<DemoUser>,
    selectedUserIndex: Int,
    onUserSelect: (Int) -> Unit,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 16.dp, vertical = 4.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        items(users.indices.toList()) { index ->
            val user = users[index]
            val selected = index == selectedUserIndex
            DemoCard(
                onClick = { onUserSelect(index) },
                accent = if (selected) Color(0xFFB36A37) else Color.Transparent,
                background = if (selected) Color(0xFFFFF8F2) else Color.White,
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    AvatarBubble(letter = user.nickname.take(1), tint = Color(0xFFB36A37))
                    Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Row {
                            Text(user.nickname, style = MaterialTheme.typography.titleSmall, modifier = Modifier.weight(1f))
                            FilterChip(selected = true, onClick = {}, label = { Text("VIP ${user.vipLevel}") })
                        }
                        Text("visitorUid: ${user.visitorUid}")
                        Text(if (selected) "当前身份，点击可切换到其他用户" else "点击切换到该访客身份")
                    }
                }
            }
        }
    }
}

@SuppressLint("SetJavaScriptEnabled")
@Composable
private fun ChatScreen(destination: ChatDestination, onBack: () -> Unit, onDetailOpen: (DetailDestination) -> Unit) {
    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .statusBarsPadding()
                    .padding(horizontal = 8.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                IconButton(onClick = onBack) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "返回")
                }
                Text(destination.title, style = MaterialTheme.typography.titleMedium)
            }
        },
    ) { innerPadding ->
        AndroidView(
            modifier = Modifier.fillMaxSize().padding(innerPadding),
            factory = { context ->
                WebView(context).apply {
                    settings.javaScriptEnabled = true
                    settings.domStorageEnabled = true
                    settings.useWideViewPort = true
                    settings.loadWithOverviewMode = false
                    addJavascriptInterface(
                        BubbleClickJavascriptBridge { packet ->
                            onDetailOpen(detailDestinationFromPacket(packet) ?: return@BubbleClickJavascriptBridge)
                        },
                        "BytedeskBubbleBridge",
                    )
                    webViewClient = object : WebViewClient() {
                        override fun onPageFinished(view: WebView?, url: String?) {
                            super.onPageFinished(view, url)
                            view?.evaluateJavascript(ANDROID_VIEWPORT_FIX_SCRIPT, null)
                            view?.evaluateJavascript(BUBBLE_CLICK_BRIDGE_SCRIPT, null)
                        }
                    }
                    webChromeClient = WebChromeClient()
                    loadUrl(destination.url)
                }
            },
            update = { webView ->
                if (webView.url != destination.url) {
                    webView.loadUrl(destination.url)
                }
                webView.evaluateJavascript(ANDROID_VIEWPORT_FIX_SCRIPT, null)
                webView.evaluateJavascript(BUBBLE_CLICK_BRIDGE_SCRIPT, null)
            },
        )
    }
}

private class BubbleClickJavascriptBridge(
    private val onMessage: (String) -> Unit,
) {
    @JavascriptInterface
    fun postMessage(message: String) {
        onMessage(message)
    }
}

@Composable
private fun GoodsDetailScreen(goods: JSONObject, onBack: () -> Unit) {
    Scaffold(
        containerColor = Color(0xFFF4F7F2),
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .statusBarsPadding()
                    .padding(horizontal = 8.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                IconButton(onClick = onBack) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "返回")
                }
                Text("商品详情", style = MaterialTheme.typography.titleMedium)
            }
        },
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(innerPadding),
            contentPadding = androidx.compose.foundation.layout.PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            item {
                DemoCard(accent = Color(0xFFE2ECE4), background = Color.White) {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        val imageUrl = goods.optString("image")
                        if (imageUrl.isNotBlank()) {
                            AsyncImage(
                                model = imageUrl,
                                contentDescription = "商品图片",
                                modifier = Modifier.fillMaxWidth().height(220.dp).clip(RoundedCornerShape(18.dp)),
                            )
                        }
                        Text("商品详情", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = Color(0xFF173127))
                        DetailRow("商品 UID", goods.optString("uid"))
                        DetailRow("标题", goods.optString("title"))
                        DetailRow("价格", formatCurrency(goods.opt("price")))
                        DetailRow("数量", goods.opt("quantity")?.toString() ?: "1")
                        DetailRow("店铺", goods.optString("shopUid"))
                        DetailRow("描述", goods.optString("description"), multiline = true)
                        DetailRow("标签", formatTagList(goods.optJSONArray("tagList")), multiline = true, showDivider = false)
                    }
                }
            }
            item {
                DemoCard(accent = Color(0xFFE2ECE4), background = Color.White) {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Text("原始载荷", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                        Card(
                            colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F7F5)),
                            shape = RoundedCornerShape(18.dp),
                        ) {
                            Text(goods.toString(2), modifier = Modifier.padding(14.dp), color = Color(0xFF365244))
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun OrderDetailScreen(order: JSONObject, onBack: () -> Unit) {
    val shipping = order.optJSONObject("shippingAddress") ?: JSONObject()
    val goods = order.optJSONObject("goods") ?: JSONObject()
    Scaffold(
        containerColor = Color(0xFFF6F3EF),
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .statusBarsPadding()
                    .padding(horizontal = 8.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                IconButton(onClick = onBack) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "返回")
                }
                Text("订单详情", style = MaterialTheme.typography.titleMedium)
            }
        },
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(innerPadding),
            contentPadding = androidx.compose.foundation.layout.PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            item {
                DemoCard(accent = Color(0xFFF0E6DA), background = Color.White) {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Text("订单详情", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = Color(0xFF3B2416))
                        DetailRow("订单 UID", order.optString("uid"))
                        DetailRow("访客 UID", order.optString("visitorUid"))
                        DetailRow("店铺", order.optString("shopUid"))
                        DetailRow("状态", firstNonBlank(order.optString("statusText"), order.optString("status")))
                        DetailRow("总金额", formatCurrency(order.opt("totalAmount")))
                        DetailRow("支付方式", order.optString("paymentMethod"))
                        DetailRow("商品", goods.optString("title"), multiline = true)
                        DetailRow("收货地址", formatShippingAddress(shipping), multiline = true, showDivider = false)
                    }
                }
            }
            item {
                DemoCard(accent = Color(0xFFF0E6DA), background = Color.White) {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Text("原始载荷", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                        Card(
                            colors = CardDefaults.cardColors(containerColor = Color(0xFFFAF7F3)),
                            shape = RoundedCornerShape(18.dp),
                        ) {
                            Text(order.toString(2), modifier = Modifier.padding(14.dp), color = Color(0xFF694B35))
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun DetailRow(label: String, value: String, multiline: Boolean = false, showDivider: Boolean = true) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = if (multiline) Alignment.Top else Alignment.CenterVertically,
        ) {
            Text(
                text = label,
                modifier = Modifier.width(88.dp),
                color = Color(0xFF6A7B72),
                style = MaterialTheme.typography.bodyMedium,
            )
            Spacer(modifier = Modifier.size(12.dp))
            Text(
                text = value.ifBlank { "-" },
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.End,
                color = Color(0xFF173127),
            )
        }
        if (showDivider) {
            HorizontalDivider(color = Color(0xFFE8EEEA))
        }
    }
}

@Composable
private fun DemoCard(
    onClick: (() -> Unit)? = null,
    accent: Color = Color.Transparent,
    background: Color = Color.White,
    content: @Composable () -> Unit,
) {
    Card(
        modifier = Modifier.fillMaxWidth().then(if (onClick != null) Modifier.clickable(onClick = onClick) else Modifier),
        colors = CardDefaults.cardColors(containerColor = background),
        shape = RoundedCornerShape(24.dp),
        border = BorderStroke(1.dp, accent),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
    ) {
        Box(modifier = Modifier.padding(16.dp)) {
            content()
        }
    }
}

@Composable
private fun AvatarBubble(letter: String, tint: Color) {
    Box(
        modifier = Modifier.size(48.dp).clip(CircleShape).background(tint.copy(alpha = 0.14f)),
        contentAlignment = Alignment.Center,
    ) {
        Text(text = letter, color = tint, fontWeight = FontWeight.Bold)
    }
}

private suspend fun fetchVisitorThreads(visitorProfile: DemoUser, searchText: String): List<ThreadSummary> = withContext(Dispatchers.IO) {
    val query = mapOf(
        "orgUid" to DEFAULT_CHAT_PROFILE.org,
        "visitorUid" to visitorProfile.visitorUid,
        "pageNumber" to "0",
        "pageSize" to "10",
        "mergeByTopic" to "true",
        "searchText" to searchText,
    ).entries.joinToString("&") {
        "${it.key}=${URLEncoder.encode(it.value, StandardCharsets.UTF_8.toString())}"
    }
    val url = URL("${API_BASE_URL.trimEnd('/')}/visitor/api/v1/threads?$query")
    val connection = (url.openConnection() as HttpURLConnection).apply {
        requestMethod = "GET"
        connectTimeout = 10000
        readTimeout = 10000
    }
    try {
        val code = connection.responseCode
        val stream = if (code in 200..299) connection.inputStream else connection.errorStream
        val body = stream?.bufferedReader()?.use(BufferedReader::readText).orEmpty()
        if (code !in 200..299) {
            throw IllegalStateException("HTTP $code")
        }
        val payload = JSONObject(body)
        if (payload.optInt("code") != 200) {
            throw IllegalStateException(payload.optString("message", "加载历史会话失败"))
        }
        val content = payload.optJSONObject("data")?.optJSONArray("content") ?: JSONArray()
        buildList {
            for (index in 0 until content.length()) {
                val item = content.optJSONObject(index) ?: continue
                add(ThreadSummary.fromJson(item))
            }
        }
    } finally {
        connection.disconnect()
    }
}

private fun sceneTitle(scene: BizScene): String {
    if (scene.value == "plain") return "普通客服会话"
    return if (scene.value.startsWith("order")) "订单客服会话" else "商品客服会话"
}

private fun buildSceneChatUrl(scene: BizScene, user: DemoUser): String {
    var bizKey: String? = null
    var bizPayload: String? = null
    var autoSendBizInfo: String? = null
    if (scene.value.startsWith("goods")) {
        bizKey = "goodsInfo"
        bizPayload = goodsInfoDemo.toString()
        autoSendBizInfo = if (scene.autoSendBizInfo) "1" else "0"
    } else if (scene.value.startsWith("order")) {
        bizKey = "orderInfo"
        val payload = JSONObject(orderInfoDemoBase.toString())
        payload.put("visitorUid", user.visitorUid)
        bizPayload = payload.toString()
        autoSendBizInfo = if (scene.autoSendBizInfo) "1" else "0"
    }
    return buildChatUrl(visitorProfile = user, bizKey = bizKey, bizPayload = bizPayload, autoSendBizInfo = autoSendBizInfo)
}

private fun buildChatUrl(
    chatProfile: ChatProfile = DEFAULT_CHAT_PROFILE,
    visitorProfile: DemoUser,
    bizKey: String? = null,
    bizPayload: String? = null,
    autoSendBizInfo: String? = null,
): String {
    val params = linkedMapOf(
        "org" to chatProfile.org,
        "t" to chatProfile.type,
        "sid" to chatProfile.sid,
        "lang" to "zh-cn",
        "navbar" to "0",
        "visitorUid" to visitorProfile.visitorUid,
        "nickname" to visitorProfile.nickname,
        "avatar" to visitorProfile.avatar,
    )
    if (!autoSendBizInfo.isNullOrBlank()) {
        params["autoSendBizInfo"] = autoSendBizInfo
    }
    if (!bizKey.isNullOrBlank() && !bizPayload.isNullOrBlank()) {
        params[bizKey] = bizPayload
    }
    val query = params.entries.joinToString("&") {
        "${it.key}=${URLEncoder.encode(it.value, StandardCharsets.UTF_8.toString())}"
    }
    return "${CHAT_BASE_URL.replace(Regex("/?chat(?:/thread)?/?$"), "")}/chat?$query"
}

private fun resolveThreadRouteTarget(thread: JSONObject): ThreadRouteTarget {
    return when (normalizeThreadType(thread)) {
        "0" -> ThreadRouteTarget("0", thread.optJSONObject("agentProtobuf")?.optString("uid").orEmpty())
        "1" -> ThreadRouteTarget("1", thread.optJSONObject("workgroupProtobuf")?.optString("uid").orEmpty())
        "2" -> ThreadRouteTarget("2", thread.optJSONObject("robotProtobuf")?.optString("uid").orEmpty())
        else -> ThreadRouteTarget("0", thread.optString("uid"))
    }
}

private fun normalizeThreadType(thread: JSONObject): String {
    return when (thread.optString("type").uppercase(Locale.ROOT)) {
        "AGENT", "0" -> "0"
        "WORKGROUP", "1" -> "1"
        "ROBOT", "2" -> "2"
        else -> ""
    }
}

private fun resolveThreadName(thread: JSONObject): String {
    val values = listOf(
        thread.optJSONObject("agentProtobuf")?.optString("nickname").orEmpty(),
        thread.optJSONObject("workgroupProtobuf")?.optString("nickname").orEmpty(),
        thread.optJSONObject("robotProtobuf")?.optString("nickname").orEmpty(),
        thread.optJSONObject("user")?.optString("nickname").orEmpty(),
        thread.optString("topic"),
    )
    return values.firstOrNull { it.isNotBlank() }?.let(::translateThreadText) ?: "未命名会话"
}

private fun resolveThreadAvatar(thread: JSONObject): String {
    return listOf(
        thread.optJSONObject("agentProtobuf")?.optString("avatar").orEmpty(),
        thread.optJSONObject("workgroupProtobuf")?.optString("avatar").orEmpty(),
        thread.optJSONObject("robotProtobuf")?.optString("avatar").orEmpty(),
        thread.optJSONObject("user")?.optString("avatar").orEmpty(),
    ).firstOrNull { it.isNotBlank() }.orEmpty()
}

private fun resolveThreadPreview(thread: JSONObject): String {
    val directPreview = normalizeText(thread.optJSONObject("contentObject")?.opt("preview") ?: thread.opt("content") ?: thread.opt("topic"))
    if (directPreview.isNotBlank()) {
        return translateThreadText(directPreview)
    }
    val payload = parseJsonObject(thread.opt("content"))
    val rawType = payload.optString("type", payload.optString("msgType", payload.optString("messageType"))).uppercase(Locale.ROOT)
    if (rawType.isBlank()) {
        return "暂无消息"
    }
    val previewType = threadPreviewLabels.firstOrNull { rawType.contains(it.match) }
    val detail = translateThreadText(extractText(payload))
    return if (previewType == null) {
        detail.ifBlank { "暂无消息" }
    } else if (detail.isBlank()) {
        previewType.fallback
    } else {
        "${previewType.fallback} · $detail"
    }
}

private fun normalizeText(value: Any?): String {
    if (value == null) return ""
    if (value is JSONArray) {
        for (index in 0 until value.length()) {
            val text = normalizeText(value.opt(index))
            if (text.isNotBlank()) return text
        }
        return ""
    }
    if (value is JSONObject) {
        return extractText(value)
    }
    val text = value.toString()
        .replace(Regex("<br\\s*/?>", RegexOption.IGNORE_CASE), "\n")
        .replace(Regex("</(p|div|li|h[1-6])>", RegexOption.IGNORE_CASE), "\n")
        .replace(Regex("<[^>]+>"), " ")
        .replace("&nbsp;", " ")
        .replace("&amp;", "&")
        .replace(Regex("\\s+"), " ")
        .trim()
    if (text.isBlank()) return ""
    return if ((text.startsWith("{") && text.endsWith("}")) || (text.startsWith("[") && text.endsWith("]"))) {
        val parsed = runCatching { JSONObject(text) }.getOrNull()
        if (parsed != null) extractText(parsed) else text
    } else {
        text
    }
}

private fun extractText(value: JSONObject): String {
    val preferredKeys = listOf(
        "label", "title", "name", "text", "content", "summary", "description", "address", "detail",
        "phoneNumber", "phone", "mobile", "emailAddress", "email", "wechatNumber", "wechat", "orderNo", "ticketNo", "uid",
    )
    preferredKeys.forEach { key ->
        val text = normalizeText(value.opt(key))
        if (text.isNotBlank()) return text
    }
    value.keys().forEach { key ->
        val text = normalizeText(value.opt(key))
        if (text.isNotBlank()) return text
    }
    return ""
}

private fun translateThreadText(value: String): String = threadTranslations[value] ?: value

private fun parseJsonObject(value: Any?): JSONObject {
    return when (value) {
        is JSONObject -> value
        is String -> runCatching { JSONObject(value) }.getOrDefault(JSONObject())
        else -> JSONObject()
    }
}

private fun parseMaybeJsonValue(value: Any?): Any? {
    if (value !is String) return value
    val text = value.trim()
    if (!((text.startsWith("{") && text.endsWith("}")) || (text.startsWith("[") && text.endsWith("]")))) {
        return value
    }
    return runCatching { JSONObject(text) }.getOrElse { value }
}

private fun normalizeBubbleClickEvent(packet: Any?): BubbleClickEvent? {
    var candidate: Any? = packet ?: return null
    if (candidate is String) {
        candidate = parseMaybeJsonValue(candidate)
    }
    if (candidate is JSONArray) {
        for (index in 0 until candidate.length()) {
            val normalized = normalizeBubbleClickEvent(candidate.opt(index))
            if (normalized != null) return normalized
        }
        return null
    }
    if (candidate is JSONObject) {
        if (candidate.has("detail") && candidate.optJSONObject("detail")?.has("data") == true) {
            return normalizeBubbleClickEvent(candidate.optJSONObject("detail")?.opt("data"))
        }
        if (candidate.has("data") && !candidate.has("type")) {
            return normalizeBubbleClickEvent(candidate.opt("data"))
        }
        if (candidate.optString("type") == MESSAGE_BUBBLE_CLICK_EVENT_NAME) {
            return BubbleClickEvent(
                uid = candidate.optString("uid"),
                type = candidate.optString("clickedMessageType"),
                content = parseMaybeJsonValue(candidate.opt("content")),
                extra = parseMaybeJsonValue(candidate.opt("extra")),
            )
        }
        if (candidate.optString("uid").isNotBlank() && candidate.optString("type").isNotBlank()) {
            return BubbleClickEvent(
                uid = candidate.optString("uid"),
                type = candidate.optString("type"),
                content = parseMaybeJsonValue(candidate.opt("content")),
                extra = parseMaybeJsonValue(candidate.opt("extra")),
            )
        }
    }
    return null
}

private fun recordFromValue(value: Any?): JSONObject {
    val parsed = parseMaybeJsonValue(value)
    return when (parsed) {
        is JSONObject -> parsed
        else -> JSONObject().apply { put("value", parsed ?: JSONObject.NULL) }
    }
}

private fun detailDestinationFromPacket(packet: Any?): DetailDestination? {
    val event = normalizeBubbleClickEvent(packet) ?: return null
    return when (event.type.uppercase(Locale.ROOT)) {
        GOODS_MESSAGE_TYPE -> DetailDestination(GOODS_MESSAGE_TYPE, resolveBubbleDetailPayload(event))
        ORDER_MESSAGE_TYPE -> DetailDestination(ORDER_MESSAGE_TYPE, resolveBubbleDetailPayload(event))
        else -> null
    }
}

private fun resolveBubbleDetailPayload(event: BubbleClickEvent): JSONObject {
    val contentRecord = recordFromValue(event.content)
    if (!contentRecord.has("value") || contentRecord.length() > 1) {
        return contentRecord
    }

    val extraRecord = recordFromValue(event.extra)
    if (!extraRecord.has("value") || extraRecord.length() > 1) {
        return extraRecord
    }

    return contentRecord
}

private fun formatCurrency(value: Any?): String {
    val number = value?.toString()?.toDoubleOrNull() ?: 0.0
    return "¥%.2f".format(Locale.getDefault(), number)
}

private fun formatTagList(tagList: JSONArray?): String {
    if (tagList == null || tagList.length() == 0) return "-"
    return buildList {
        for (index in 0 until tagList.length()) {
            add(tagList.opt(index).toString())
        }
    }.joinToString(" / ")
}

private fun formatShippingAddress(shippingAddress: JSONObject): String {
    val values = listOf(
        shippingAddress.optString("name"),
        shippingAddress.optString("phone"),
        shippingAddress.optString("address"),
    ).filter { it.isNotBlank() }
    return if (values.isEmpty()) "-" else values.joinToString(" / ")
}

private fun firstNonBlank(vararg values: String): String = values.firstOrNull { it.isNotBlank() } ?: ""

private fun formatTime(value: String): String {
    if (value.isBlank()) return ""
    val patterns = listOf(
        "yyyy-MM-dd'T'HH:mm:ss.SSSX",
        "yyyy-MM-dd'T'HH:mm:ssX",
        "yyyy-MM-dd HH:mm:ss",
    )
    val parsed = patterns.firstNotNullOfOrNull { pattern ->
        runCatching { SimpleDateFormat(pattern, Locale.getDefault()).parse(value) }.getOrNull()
    } ?: return value
    return SimpleDateFormat("MM-dd HH:mm", Locale.getDefault()).format(parsed ?: Date())
}
