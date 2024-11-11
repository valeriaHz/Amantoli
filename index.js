const express = require('express');
const mysql = require('mysql');
const bodyParse = require('body-parser');

const app = express()

app.use(bodyParse.json())

const puerto = 3000;

const conexion = mysql.createConnection({
    host:'localhost',
    database:'store',
    user:'root',
    pass:''
})

app.listen(puerto,()=>{
    console.log(`Servidor corriendo en el puerto ${puerto}`);
})

conexion.connect(error => {
    if(error) throw error
    console.log('Conexion exitosa a la base de datos');
})

app.get('/',(req,res)=>{
    res.send(API)
})

app.get('/appObtenerUsuarios',(req,res)=>{
    const query = 'SELECT * FROM t_usuarios'
    conexion.query(query,(error,results)=>{
        if(error) return console.error(error.message)

            if(results.length > 0){
                res.json(results)
            }else{
                res.json("No hay registros")
            }
    })
})

app.post('/appInicioSesion',(req,res)=>{
    const correo = req.body.correo
    const contraseña = req.body.contraseña

    const query = 'SELECT * FROM t_usuarios WHERE correo = ? AND  contraseña = ?';

    conexion.query(query,[correo,contraseña],(error,results)=>{
        if(error){
            consolo.error(error)
            return res.status(500).json({error: "Error del servidor"})
        }
        if(results.length > 0){
            res.json({message: 'Login Exitoso', usuarios:results[0]})
        }else{
            res.status(401).json({error: 'Credenciales invalidas'})
        }
    })
})

app.post('/appAgregarUsuario', (req,res)=>{
    const usuario = {
        nombre: req.body.nombre,
        apellidoPat: req.body.apellidoPat,
        apellidoMat: req.body.apellidoMat,
        edad: req.body.edad,
        genero: req.body.genero,
        correo: req.body.correo,
        contraseña: req.body.contraseña,
        fechaNac: req.body.fechaNac
    }

    const query = 'INSERT INTO t_usuarios SET ?;'

    conexion.query(query,usuario,(error)=>{
        if(error) return console.error(error.message);
        res.json('Se inserto correctamente el usuario')
    })
})

app.put('/appActualizarUsuario/:id', (req,res)=>{
    const {id} = req.params
    const {nombre, apellidoPat, apellidoMat, edad, genero, correo, contraseña,fachaNac} = req.body

    const query ="UPDATE t_usuarios SET nombre='${nombre}', apellidoPat= '${apellidoPat}', apellidoMat='${apellidoMat}', edad='${edad}', genero='${genero}', correo='${correo}', contraseña='${contraseña}', fechaNac='${fechaNac}' WHERE id_usuario='${id}'"

    conexion.query(query, (error)=>{
        if(error)return console.error(error.message)

            res.json('Se actualizo correctamente el usuario')
    })
})

app.delete('/appEliminarUsuario/:id', (req,res)=>{
    const {id} = req.params

    const query = "DELETE FROM t_usuarios WHERE id_usuario=${id}"

    conexion.query(query, (error)=>{
        if(error)console.error(error.message)

            res.json('Se elimino correctamente el usuario')
    })
})
