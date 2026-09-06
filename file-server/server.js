const express = require('express');
const multer  = require('multer');
const cors    = require('cors');
const path    = require('path');
const fs      = require('fs');

const app = express();
app.use(cors());

// Tạo thư mục uploads nếu chưa có
const uploadDir = path.join(__dirname, 'uploads');
if (!fs.existsSync(uploadDir)) fs.mkdirSync(uploadDir);

// Cấu hình lưu file vào thư mục uploads/
const storage = multer.diskStorage({
  destination: (req, file, cb) => cb(null, uploadDir),
  filename:    (req, file, cb) => cb(null, file.originalname)
});
const upload = multer({ storage });

// =====================================================================
// API 1: Upload Single File
// POST http://localhost:8080/uploadFile
// =====================================================================
app.post('/uploadFile', upload.single('file'), (req, res) => {
  if (!req.file) return res.status(400).json({ message: 'No file uploaded' });
  res.json({
    fileName:     req.file.originalname,
    fileType:     req.file.mimetype,
    size:         req.file.size,
    fileDownloadUri: `http://localhost:8080/downloadFile/${req.file.originalname}`,
    message:      'File uploaded successfully!'
  });
});

// =====================================================================
// API 2: Upload Multiple Files
// POST http://localhost:8080/uploadMultipleFiles
// =====================================================================
app.post('/uploadMultipleFiles', upload.array('files', 10), (req, res) => {
  if (!req.files || req.files.length === 0) return res.status(400).json({ message: 'No files uploaded' });
  const result = req.files.map(file => ({
    fileName:     file.originalname,
    fileType:     file.mimetype,
    size:         file.size,
    fileDownloadUri: `http://localhost:8080/downloadFile/${file.originalname}`,
    message:      'File uploaded successfully!'
  }));
  res.json(result);
});

// =====================================================================
// API 3: Download File
// GET http://localhost:8080/downloadFile/{fileName}
// =====================================================================
app.get('/downloadFile/:fileName', (req, res) => {
  const filePath = path.join(uploadDir, req.params.fileName);
  if (!fs.existsSync(filePath)) return res.status(404).json({ message: 'File not found: ' + req.params.fileName });
  res.download(filePath, req.params.fileName);
});

// =====================================================================
// Trang chủ: Giao diện HTML giống Spring Boot example
// GET http://localhost:8080
// =====================================================================
app.get('/', (req, res) => {
  res.send(`<!DOCTYPE html>
<html>
<head>
  <title>Spring Boot File Upload / Download Rest API Example</title>
  <style>
    * { box-sizing: border-box; margin: 0; padding: 0; }
    body { background: #1976d2; min-height: 100vh; display: flex; align-items: center; justify-content: center; font-family: Arial, sans-serif; }
    .card { background: white; border-radius: 4px; padding: 40px 50px; width: 580px; box-shadow: 0 2px 10px rgba(0,0,0,0.2); }
    h2 { font-size: 20px; font-weight: bold; margin-bottom: 30px; color: #212121; }
    h4 { font-size: 15px; font-weight: bold; margin-bottom: 12px; color: #212121; }
    .group { margin-bottom: 30px; }
    .input-row { display: flex; border: 1px solid #ccc; border-radius: 2px; overflow: hidden; }
    .file-label { padding: 8px 14px; background: #e0e0e0; border-right: 1px solid #ccc; white-space: nowrap; cursor: pointer; font-size: 14px; }
    .file-name { padding: 8px 14px; flex: 1; font-size: 14px; color: #757575; line-height: 1.5; }
    input[type=file] { display: none; }
    .btn { background: #1976d2; color: white; border: none; padding: 8px 24px; font-size: 14px; cursor: pointer; margin-left: 10px; border-radius: 2px; }
    .btn:hover { background: #1565c0; }
    .result { margin-top: 12px; font-size: 13px; color: #388e3c; background: #f1f8e9; padding: 8px 12px; border-radius: 4px; display: none; word-break: break-all; }
    .bottom-row { display: flex; align-items: center; }
  </style>
</head>
<body>
<div class="card">
  <h2>Spring Boot File Upload / Download Rest API Example</h2>

  <div class="group">
    <h4>Upload Single File</h4>
    <div class="bottom-row">
      <div class="input-row" style="flex:1">
        <label class="file-label" for="singleFile">Choose File</label>
        <input type="file" id="singleFile" onchange="document.getElementById('singleName').textContent=this.files[0]?.name||'No file chosen'">
        <span class="file-name" id="singleName">No file chosen</span>
      </div>
      <button class="btn" onclick="uploadSingle()">Submit</button>
    </div>
    <div class="result" id="singleResult"></div>
  </div>

  <div class="group">
    <h4>Upload Multiple Files</h4>
    <div class="bottom-row">
      <div class="input-row" style="flex:1">
        <label class="file-label" for="multiFile">Choose Files</label>
        <input type="file" id="multiFile" multiple onchange="document.getElementById('multiName').textContent=[...this.files].map(f=>f.name).join(', ')||'No file chosen'">
        <span class="file-name" id="multiName">No file chosen</span>
      </div>
      <button class="btn" onclick="uploadMultiple()">Submit</button>
    </div>
    <div class="result" id="multiResult"></div>
  </div>
</div>

<script>
async function uploadSingle() {
  const file = document.getElementById('singleFile').files[0];
  if (!file) return alert('Vui lòng chọn file!');
  const fd = new FormData(); fd.append('file', file);
  const res = await fetch('/uploadFile', { method: 'POST', body: fd });
  const data = await res.json();
  const el = document.getElementById('singleResult');
  el.style.display = 'block';
  el.innerHTML = '✅ ' + JSON.stringify(data, null, 2).replace(/\\n/g, '<br>');
}

async function uploadMultiple() {
  const files = document.getElementById('multiFile').files;
  if (!files.length) return alert('Vui lòng chọn ít nhất 1 file!');
  const fd = new FormData();
  for (const f of files) fd.append('files', f);
  const res = await fetch('/uploadMultipleFiles', { method: 'POST', body: fd });
  const data = await res.json();
  const el = document.getElementById('multiResult');
  el.style.display = 'block';
  el.innerHTML = '✅ Uploaded ' + data.length + ' file(s): ' + data.map(f => f.fileName).join(', ');
}
</script>
</body>
</html>`);
});

// =====================================================================
app.listen(8080, () => {
  console.log('✅ File Upload/Download server đang chạy tại http://localhost:8080');
  console.log('   POST /uploadFile              - Upload 1 file');
  console.log('   POST /uploadMultipleFiles     - Upload nhiều file');
  console.log('   GET  /downloadFile/{fileName} - Download file');
});
