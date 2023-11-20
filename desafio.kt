/*
[Template no Kotlin Playground](https://pl.kotl.in/0d_MW9itF) - By: William Elesbão

Ano: 2023/Nov/20

Instituição: DIO
Desafio**

comentário: Realizado comentário dos trechos/blocos de código afim de auxiliar o entendimento de cada sessão do código.

Links/Redes:

GitHub      - https://github.com/WilliamElesbao
Twitter (X) - https://twitter.com/ElesbaoWilliam
Instagram   - https://www.instagram.com/willtubetech/
YouTube     - https://www.youtube.com/@willtubetech

 */ 


// Importa a classe AtomicInteger → op. atomicas de incremento e acesso a um valor inteiro.
import java.util.concurrent.atomic.AtomicInteger

// criação de um objeto para gerar ID's únicos para cada Curso e Aluno do sistema
object IdGenerator{

    // variáveis que armazenam o último ID usado para cursos e usuários. inicializados com o valor 0.
    private val cursoId = AtomicInteger(0)
    private val usuarioId = AtomicInteger(0)
    
    //funções para obter os próximos IDs para cursos e usuários
    fun getNextCursoId(): String{
        return(cursoId.incrementAndGet() + 1000).toString().substring(2)
    }

    fun getNextUsuarioId(): String{
        return(usuarioId.incrementAndGet() + 1000).toString().substring(1)
    }
}

// enum é uma estrutura de dados que representa um conjunto fixo de valores constantes.
enum class Nivel{
    BASICO, INTERMEDIARIO, AVANCADO
}

// open, permite que a classe seja herdada por outras classes. Isso significa que essa classe pode ser estendida por outras classes.
open class Usuario(val id: String = IdGenerator.getNextUsuarioId(), val nome: String, var email: String, var idade: Int, val funcao: String){
    override fun toString(): String{
        return "ID: $id, Nome: $nome, E-mail: $email, Idade: $idade, função: $funcao"
    }
}

/*
Se você não fornecer um ID ao criar um curso, o IdGenerator.getNextCursoId() será chamado automaticamente
para gerar um novo ID único para esse curso. Além disso, a propriedade alunosMatriculados é uma lista vazia 
no momento da criação do curso, permitindo que você adicione usuários matriculados posteriormente.
*/ 
data class Curso(val id: String = IdGenerator.getNextCursoId(), val nome: String, val nivel: Nivel, val duracaoHoras: Int){
    val alunosMatriculados = mutableListOf<Usuario>() // lista mutável (mutableListOf) de usuários (Usuario). Esta lista mantém os usuários matriculados neste curso.

    override fun toString(): String{
        return "ID: $id, Nome: $nome, Nível: $nivel, Duração(horas): $duracaoHoras"
    }
}

// a classe Professor que herda da classe Usuario e adiciona funcionalidades específicas para professores.
/*
Herança:
Professor é uma subclasse de Usuario (representado por : Usuario), o que significa que um Professor é um tipo especial de Usuario.

Construtor:
O construtor de Professor recebe um objeto Usuario como parâmetro.
Usa a notação : Usuario(...) após o nome da classe para chamar o construtor da superclasse Usuario.
Esse construtor cria um Usuario usando os mesmos atributos do Usuario fornecido, mas com a função definida como "Professor".
*/
class Professor(usuario: Usuario) : Usuario(usuario.id, usuario.nome, usuario.email, usuario.idade, "Professor"){
    val cursosLecionados = mutableListOf<Curso>() // é uma propriedade da classe Professor que mantém uma lista mutável de cursos (Curso) que este professor leciona.

    fun lecionar(curso: Curso){
        cursosLecionados.add(curso)
    }
}

/*
classe Sistema responsavel por guardar informações sobre os alunos(as), cursos e professores(as).
*/
class Sistema{
    val usuarios = mutableListOf<Usuario>()
    val cursos = mutableListOf<Curso>()
    val professores = mutableListOf<Professor>()

// cadastrarUsuário: cadastra um novo usuário no sistema (aluno/professor).
    fun cadastrarUsuario(usuario: Usuario){
        usuarios.add(usuario)
        if(usuario.funcao == "Professor"){
            val professor = Professor(usuario)
            professores.add(professor)
        }
    }
// cadastrarCurso: cadastra um novo curso no sistema (curso/disciplina).
    fun cadastrarCurso(curso: Curso){
        cursos.add(curso)
    }

// matricularAluno: matricula o(a) aluno(a) a um curso
    fun matricularAluno(usuario: Usuario, curso: Curso){
        val aluno = usuarios.find{it.id == usuario.id}
        if(aluno != null){
            curso.alunosMatriculados.add(aluno)
        }else{
            println("Aluno $aluno não encontrado.")
        }
    }

// lista todos os cursos cadastrados
    fun listarCursos(){
        println("Cursos cadastrados:")
        cursos.forEach{println(it)}
    }

// lista todos os usuarios cadastrados(incluindo professores)
    fun listarUsuarios(){
        println("\nTodos os usuários cadastrados:")
        usuarios.forEach{ println(it) }

        println("\nProfessores:")
        professores.forEach{ println(it) }
    }

// lista todos os alunos(as) cadastrados e seus respectivos cursos nos quais estão matriculados/inscritos
    fun listarAlunosPorCurso(){
        cursos.forEach { curso -> 
            println("\nAlunos matriculados no curso ${curso.nome}")
            curso.alunosMatriculados.forEach { aluno -> 
                println("Curso: ${curso.nome}, Aluno: ${aluno.nome}")
            }
        }
    }
}

// funcao principal na qual será executado o código e será invocado as classes e funções criadas anteriormente
fun main(){
    // inicialização do nosso sistema, onde irá armazenar nossas informações
    val sistema = Sistema()

    // cadastro dos seguintes cursos, com Nome, Nivel e Duração(horas)
    val curso1 = Curso(nome = "Desenvolvimento Backend com Kotlin", nivel = Nivel.BASICO, duracaoHoras = 52)
    val curso2 = Curso(nome = "Ciência de Dados com Python", nivel = Nivel.INTERMEDIARIO, duracaoHoras = 80)

    // invoca a função cadastrarCurso da nossa classe Sistema e cadastra os nossos cursos acima
    sistema.cadastrarCurso(curso1)
    sistema.cadastrarCurso(curso2)

    // cadastra os usuários(aluno/professor)
    val aluno1 = Usuario(nome = "William Elesbão", email = "william.elesbao.2000@gmail.com", idade = 23, funcao = "Aluno")
    val aluno2 = Usuario(nome = "Andressa Arruda", email = "andressaarruda4000@gmail.com", idade = 25, funcao = "Aluno")
    val professor1 = Usuario(nome = "FalvoJr", email = "falvojr@email.com", idade = 35, funcao = "Professor")

    //invoca a função cadastrarUsuario da classe Sistema, e define se é um aluno ou professor
    sistema.cadastrarUsuario(aluno1)
    sistema.cadastrarUsuario(aluno2)
    sistema.cadastrarUsuario(professor1)

    // realiza a matricula do aluno ao curso
    sistema.matricularAluno(aluno1, curso1)
    sistema.matricularAluno(aluno1, curso2)
    sistema.matricularAluno(aluno2, curso2)

    // imprime/mostra a lista dos cursos cadastrados
    sistema.listarCursos()

    // imprime/mostra a lista dos usuarios (aluno/professor) cadastrados
    sistema.listarUsuarios()

    // imprime/mostra a lista de alunos e cursos em que estão matriculados
    sistema.listarAlunosPorCurso()
}