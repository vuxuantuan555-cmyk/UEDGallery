package com.example.uedgallery

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.uedgallery.ui.theme.UEDGalleryTheme

// -------------------- DATA MODEL --------------------
data class UEDImage(
    val imageRes: Int,
    val title: String,
    val description: String
)

val uedImages = listOf(
    UEDImage(R.drawable.ued1, "Cổng Trường UED", "459 Tôn Đức Thắng, Đà Nẵng"),
    UEDImage(R.drawable.ued2, "Tòa nhà Hành chính", "Xây dựng năm 2015"),
    UEDImage(R.drawable.ued3, "Giảng đường A", "Khu giảng dạy chính"),
    UEDImage(R.drawable.ued4, "Thư viện", "Không gian học tập sinh viên"),
    UEDImage(R.drawable.ued5, "Hoạt động sinh viên", "Sự kiện ngoại khóa")
)

// -------------------- MAIN ACTIVITY --------------------
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UEDGalleryTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    GalleryScreen()
                }
            }
        }
    }
}

// -------------------- UI SCREEN --------------------
@Composable
fun GalleryScreen() {
    var currentIndex by remember { mutableIntStateOf(0) }
    val currentImage = uedImages[currentIndex]

    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    if (isLandscape) {
        // -------------------- GIAO DIỆN NGANG (KHÔNG FULL MÀN HÌNH) --------------------
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Card(
                modifier = Modifier.fillMaxSize(),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Box {
                    Image(
                        painter = painterResource(id = currentImage.imageRes),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )

                    Surface(
                        color = Color.Black.copy(alpha = 0.6f),
                        shape = RoundedCornerShape(topStart = 8.dp),
                        modifier = Modifier.align(Alignment.BottomStart)
                    ) {
                        Text(
                            text = "${currentIndex + 1}/${uedImages.size}",
                            color = Color.White,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                            fontSize = 12.sp
                        )
                    }
                }
            }

            ControlButtons(
                currentIndex = currentIndex,
                onNext = { if (currentIndex < uedImages.size - 1) currentIndex++ },
                onPrev = { if (currentIndex > 0) currentIndex-- },
                isLandscape = true,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 24.dp, start = 60.dp, end = 60.dp)
            )
        }
    } else {
        // -------------------- GIAO DIỆN DỌC (GIỮ NGUYÊN) --------------------
        Column(
            modifier = Modifier.fillMaxSize().padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "UED Gallery",
                fontSize = 28.sp,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(vertical = 16.dp)
            )

            Card(
                modifier = Modifier.fillMaxWidth().weight(1f),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                Image(
                    painter = painterResource(id = currentImage.imageRes),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = currentImage.title, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = currentImage.description, fontSize = 16.sp)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            ControlButtons(
                currentIndex = currentIndex,
                onNext = { if (currentIndex < uedImages.size - 1) currentIndex++ },
                onPrev = { if (currentIndex > 0) currentIndex-- },
                isLandscape = false
            )
        }
    }
}

// -------------------- ĐỊNH NGHĨA HÀM CONTROL BUTTONS (THIẾU PHẦN NÀY SẼ BÁO LỖI) --------------------
@Composable
fun ControlButtons(
    currentIndex: Int,
    onNext: () -> Unit,
    onPrev: () -> Unit,
    isLandscape: Boolean,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Nút Previous
        Button(
            onClick = onPrev,
            enabled = currentIndex > 0,
            modifier = Modifier.weight(1f).height(50.dp),
            shape = RoundedCornerShape(25.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (isLandscape) Color.White.copy(alpha = 0.8f) else MaterialTheme.colorScheme.primary,
                contentColor = if (isLandscape) Color.Black else Color.White
            )
        ) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text("Previous", maxLines = 1)
        }

        // Nút Next
        Button(
            onClick = onNext,
            enabled = currentIndex < uedImages.size - 1,
            modifier = Modifier.weight(1f).height(50.dp),
            shape = RoundedCornerShape(25.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (isLandscape) Color(0xFF1976D2) else MaterialTheme.colorScheme.primary
            )
        ) {
            Text("Next", fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.width(8.dp))
            Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, modifier = Modifier.size(18.dp))
        }
    }
}