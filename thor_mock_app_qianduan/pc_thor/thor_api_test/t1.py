import secrets


def generate_client_iv():
    """生成8字节安全随机IV - 对应Java的EncryptionHelper.generateIVH()"""
    # Java中使用SecureRandom生成8字节随机数
    client_iv = secrets.token_bytes(8)
    print(f"🔑 生成客户端IV: {client_iv.hex().upper()}")
    return client_iv


print(generate_client_iv())
