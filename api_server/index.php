<?php
    header('Content-Type:application/json');

    include "koneksi.php" ;
    
    // [REQUEST_METHOD] => GET
    // $conn->query($sql_script) === TRUE
    // $conn->close();
    // $row = $result->fetch_assoc()
    // echo $result->fetch_object()->keterangan ;
    switch ($_SERVER['REQUEST_METHOD']) {
        case 'GET':
            $result = $sql->query("select * from stat_pintu where id='1' ");
            echo json_encode($result->fetch_object()) ;
            $sql->close();
            break;
        case 'POST':
            $result = $sql->query("select * from stat_pintu where id='1' ");
            $data = $result->fetch_object();

            // $data->status = 0
            $data->status = !$data->status ;
            // $data->status = 1

            $data->keterangan = $data->status ? 'BUKA' : 'TUTUP' ;

            $newQuery = $sql->query("update stat_pintu set status='{$data->status}', keterangan='{$data->keterangan}' where id='1' ");
            if($newQuery == FALSE) {
                echo json_encode([
                    'stat' => false,
                    'err' => $sql->error
                ]);
                break ;
            }

            echo json_encode([
                'stat' => true,
                'msg' => 'berhasil',
                'data' => $data
            ]);

            $sql->close();
            break;
        
        default:
            echo json_encode([
                'stat' => false,
                'err' => 'REQUEST NOT FOUND',
                'msg' => 'PLESE CHECK YOUR METHOD REQUEST'
            ]);
            break;
    }