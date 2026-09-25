const multer = require("multer");
const path = require("path");

const storage = multer.diskStorage({

    destination: (req, file, cb) => {
        cb(null, "uploads/");
    },

    filename: (req, file, cb) => {

        const extension =
            path.extname(file.originalname);

        const filename =
            `${Date.now()}-${Math.round(Math.random() * 1E9)}${extension}`;

        cb(null, filename);
    }
});

const upload = multer({
    storage
});

module.exports = upload;
