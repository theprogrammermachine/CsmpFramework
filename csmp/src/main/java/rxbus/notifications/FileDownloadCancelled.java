package rxbus.notifications;

import models.DocTypes;

public class FileDownloadCancelled {

    private DocTypes docType;
    private long fileId;

    public FileDownloadCancelled(DocTypes docType, long fileId) {
        this.docType = docType;
        this.fileId = fileId;
    }

    public DocTypes getDocType() {
        return docType;
    }

    public long getFileId() {
        return fileId;
    }
}
