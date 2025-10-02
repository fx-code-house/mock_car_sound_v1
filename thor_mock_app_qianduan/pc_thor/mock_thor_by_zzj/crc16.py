class ThorCRC16:
    """用于计算和校验数据包的 CRC16."""

    def __init__(self):
        # 预先生成 CRC 计算表以提高效率
        self.crc_table = []
        for i in range(256):
            crc = i
            for _ in range(8):
                if crc & 1:
                    crc = (crc >> 1) ^ 0xA001
                else:
                    crc >>= 1
            self.crc_table.append(crc & 0xFFFF)

    def calculate(self, data: bytes) -> int:
        """计算给定数据的 CRC16 值."""
        crc = 0xFFFF
        for byte in data:
            tbl_idx = (crc ^ byte) & 0xFF
            crc = ((crc >> 8) ^ self.crc_table[tbl_idx]) & 0xFFFF
        return crc & 0xFFFF
