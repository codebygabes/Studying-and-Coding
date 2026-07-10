package br.ceub.ProjetoFinal.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ceub.ProjetoFinal.dto.AuthResponse;
import br.ceub.ProjetoFinal.dto.LoginRequest;
import br.ceub.ProjetoFinal.dto.RegisterRequest;
import br.ceub.ProjetoFinal.model.Usuario;
import br.ceub.ProjetoFinal.repository.UsuarioRepository;
import br.ceub.ProjetoFinal.security.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.crypto.password.PasswordEncoder;

// @RestController indica que esta classe é um controlador REST.
// @RequestMapping define o caminho base "/api/auth" para todos os endpoints desta classe.
// @Tag é uma anotação do Swagger/OpenAPI que agrupa os endpoints sob o nome "Autenticação"
// na documentação interativa da API.
@RestController
@RequestMapping("/api/auth")
@Tag(name = "Autenticação", description = "Login com e-mail e senha para obter JWT")
public class AuthRestController {

    // Dependências injetadas via construtor (injeção por construtor, mais recomendada que @Autowired em campos).
    // AuthenticationManager: responsável por autenticar as credenciais do usuário.
    // JwtService: responsável por gerar e validar tokens JWT.
    // UsuarioRepository: acessa a tabela de usuários no banco de dados.
    // PasswordEncoder: responsável por criptografar e verificar senhas.
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    // Construtor que recebe todas as dependências necessárias.
    // O Spring injeta automaticamente as implementações corretas ao criar este controller.
    public AuthRestController(
            AuthenticationManager authenticationManager,
            JwtService jwtService,
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Endpoint de LOGIN: autentica o usuário e retorna um token JWT.
    // @PostMapping("/login") mapeia POST para /api/auth/login
    // @Operation é uma anotação do Swagger que descreve o endpoint na documentação.
    // Fluxo do método:
    //   1. Valida se o corpo da requisição e os campos obrigatórios (email e senha) estão presentes.
    //   2. Tenta autenticar com o AuthenticationManager usando email e senha.
    //   3. Se as credenciais forem inválidas, captura BadCredentialsException e retorna 401 (Unauthorized).
    //   4. Busca o usuário no banco pelo email para obter seus dados completos.
    //   5. Gera o token JWT com os dados do usuário e retorna 200 (OK) com o token.
    @PostMapping("/login")
    @Operation(summary = "Obter token JWT", description = "Autentica pelo e-mail e pela senha cadastrados na tabela de usuários")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        if (request == null) {
            return ResponseEntity.badRequest().build();
        }
        if (request.email() == null || request.email().isBlank()
                || request.password() == null || request.password().isBlank()) {
            return ResponseEntity.badRequest().build();
        }
        try {
            // Cria um token de autenticação com email (como username) e senha,
            // e passa para o AuthenticationManager verificar no banco de dados.
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.email().trim(),
                            request.password()
                    )
            );
        } catch (BadCredentialsException e) {
            // Credenciais inválidas: retorna 401 (Unauthorized)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        // Busca o usuário no banco ignorando diferença de maiúsculas/minúsculas no email
        var usuario = usuarioRepository.findByEmailIgnoreCase(request.email().trim());
        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        // Gera o token JWT com as informações do usuário autenticado
        String token = jwtService.generateToken(usuario);
        return ResponseEntity.ok(new AuthResponse(token));
    }

    // Endpoint de REGISTRO: cria um novo usuário no sistema.
    // @PostMapping("/register") mapeia POST para /api/auth/register
    // Fluxo do método:
    //   1. Valida se o corpo da requisição e os campos obrigatórios (nome, email, senha) estão presentes.
    //   2. Verifica se já existe um usuário com o mesmo email — se sim, retorna 409 (Conflict).
    //   3. Cria um novo objeto Usuario com os dados fornecidos.
    //   4. A senha é criptografada com o PasswordEncoder antes de salvar (nunca salva em texto puro).
    //   5. Salva o novo usuário no banco e retorna 201 (Created).
    @PostMapping("/register")
    @Operation(summary = "Cadastrar um novo usuário", description = "Cria um novo usuário com nome, e-mail e senha criptografada")
    public ResponseEntity<Void> register(@RequestBody RegisterRequest request) {
        if (request == null) {
            return ResponseEntity.badRequest().build();
        }
        if (request.nome() == null || request.nome().isBlank()
                || request.email() == null || request.email().isBlank()
                || request.password() == null || request.password().isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        // Verifica se o email já está em uso para evitar duplicatas
        if (usuarioRepository.findByEmailIgnoreCase(request.email().trim()) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        // Cria o novo usuário com os dados formatados e a senha criptografada
        Usuario novoUsuario = new Usuario();
        novoUsuario.setNome(request.nome().trim());
        novoUsuario.setEmail(request.email().trim().toLowerCase());
        novoUsuario.setSenha(passwordEncoder.encode(request.password())); // Senha nunca é salva em texto puro

        usuarioRepository.save(novoUsuario);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
