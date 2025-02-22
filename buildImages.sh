docker build -t ismabc4/peliculas_backend:0.1 ./backend_peliculas
docker build -t ismabc4/usuarios_backend:0.1 ./backend_usuarios
docker build -t ismabc4/password_generator_backend:0.1 ./password_generator_backend
docker build -t ismabc4/frontend:0.1 ./frontend

docker login

docker push ismabc4/peliculas_backend:0.1
docker push ismabc4/usuarios_backend:0.1
docker push ismabc4/password_generator_backend:0.1
docker push ismabc4/frontend:0.1