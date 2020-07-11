//前端通过获取后端公钥进行RSA加密  (前端公钥加密，后端私钥解密)
function RSAEncrypt(word, publicKey) {
    var encrypt = new JSEncrypt();
    encrypt.setPublicKey(publicKey);
    var data = encrypt.encrypt(word);
    return data;
}

//前端通过获取后端私钥进行RSA解密 (前端私钥解密，后端公钥加密)
function RSAEncryptByFrontEnd(word, privateKey) {
    var encrypt = new JSEncrypt();
    encrypt.setPublicKey(privateKey);
    var data = encrypt.decrypt(word);
    return data;
}

//前端通过获取后端私钥进行RSA加密 (前端私钥加密，后端公钥加密)
function RSADecryptByFrontEnd(word, privateKey) {
    var encrypt = new JSEncrypt();
    encrypt.setPublicKey(privateKey);
    var data = encrypt.encrypt(word);
    return data;
}

//RSA解密
function RSADecrypt(word, privateKey) {
    var encrypt = new JSEncrypt();
    encrypt.setPrivateKey(privateKey);
    var data = encrypt.decrypt(word);
    return data;
}