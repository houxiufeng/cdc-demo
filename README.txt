1.开启mysql binlog.
********my.cnf********
[mysqld]
log-bin=mysql-bin
server_id=1
binlog-format=ROW
binlog_row_image=FULL
expire_logs_days=30
gtid_mode=ON
enforce_gtid_consistency=ON
default-time-zone='+8:00'

2.pom.xml 必须修改版本号为 8.0.21, 默认的8.0.27 会报错.
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>8.0.21</version>
</dependency>
